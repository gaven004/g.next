package com.g.sys.att.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.g.commons.exception.ExceedMaxSizeException;
import com.g.commons.exception.GenericAppException;
import com.g.commons.exception.IllegalArgumentException;
import com.g.commons.exception.UnsupportedFileTypeException;
import com.g.commons.utils.HexIDGenerator;
import com.g.sys.SysErrorCode;
import com.g.sys.att.model.Module;

public class FileUploader {
    private static final Logger logger = LoggerFactory.getLogger(FileUploader.class);

    private final long maxFileSize; // 上传文件大小限制

    private final Path basePath; // 上传目录

    private final int basePathNameCount; // 上传目录分节数

    private final String browseUrl; // 浏览路径

    private final HashSet<String> supportFileTypes;

    public FileUploader(long maxFileSize, String basePath, String browseUrl, HashSet<String> supportFileTypes) {
        this.maxFileSize = maxFileSize;
        this.basePath = Path.of(basePath).normalize();
        this.browseUrl = browseUrl;
        this.supportFileTypes = supportFileTypes;

        basePathNameCount = this.basePath.getNameCount();
    }

    /**
     * 根据路径获取文件名称
     *
     * @param path
     * @return
     */
    public static String getFilename(String path) {
        return FilenameUtils.getName(path);
    }

    /**
     * 取文件的扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename).toLowerCase();
    }

    /**
     * 检查上传的文件是否为支持的类型，是否在限制的大小内
     * 正常的文件，返回文件名，不包含原上传的文件目录
     *
     * @param source
     * @return
     */
    private String checkAndGet(MultipartFile source) {
        String filename = source.getOriginalFilename();
        if (supportFileTypes != null && !supportFileTypes.isEmpty()) {
            if (!supportFileTypes.contains(getExtension(filename))) {
                throw new UnsupportedFileTypeException();
            }
        }

        if (source.isEmpty()) {
            throw new IllegalArgumentException("上传文件为空");
        }

        if (maxFileSize < source.getSize()) {
            throw new ExceedMaxSizeException("上传文件大小不能超过" + String.valueOf(maxFileSize));
        }

        return getFilename(filename);
    }

    /**
     * 保存上传文件到指定的目录，返回保存basePath下的相对路径
     *
     * @param source
     * @param subPath 子目录
     * @param rename  重命名，结果会保留原文件的后缀
     * @return
     * @throws GenericAppException
     */
    public String uploadAndGetPath(@NotNull MultipartFile source, Module module, String subPath, String rename)
            throws GenericAppException {
        String filename = checkAndGet(source);
        if (StringUtils.hasText(rename)) {
            String ext = FilenameUtils.getExtension(filename);
            if (StringUtils.hasText(ext) && !rename.endsWith(ext)) {
                filename = rename + "." + ext;
            } else {
                filename = rename;
            }
        }

        Path path = basePath.resolve(module.path());
        if (StringUtils.hasText(subPath)) {
            path = path.resolve(subPath);
        }

        path = save(source, path, filename);
        int c = path.getNameCount();
        return path.subpath(basePathNameCount, c).toString();
    }

    public String uploadAndGetPath(@NotNull MultipartFile source, Module module)
            throws GenericAppException {
        return uploadAndGetPath(source, module, null, null);
    }

    /**
     * 上传文件到指定目录，并自动重命名
     */
    public String uploadAndGetPath(@NotNull MultipartFile source, Module module, String subPath, boolean rename)
            throws GenericAppException {
        String filename = null;
        if (rename) {
            filename = checkAndGet(source);
            String ext = FilenameUtils.getExtension(filename);
            filename = HexIDGenerator.getInstance().nextId() + "." + ext;
        }
        return uploadAndGetPath(source, module, subPath, filename);
    }

    /**
     * 保存上传的文件，指定保存的目录和文件名
     *
     * @param srcFile  上传的文件
     * @param path     保存的目录
     * @param filename 文件名
     * @return 保存后的文件对象
     */
    public static Path save(@NotNull MultipartFile srcFile, @NotNull Path path, @NotNull String filename) {
        logger.debug("保存上传的文件，路径：{}，文件：{}", path, filename);

        if (!Files.exists(path)) {
            synchronized (FileUploader.class) {
                // 避免并发创建时失败
                if (!Files.exists(path)) {
                    try {
                        Files.createDirectories(path);
                    } catch (IOException e) {
                        logger.warn("无法创建上传目录");
                        throw new GenericAppException(SysErrorCode.PathCreateFailed);
                    }
                }
            }
        }

        try {
            Path destFile = path.resolve(filename);
            srcFile.transferTo(destFile);
            return destFile;
        } catch (IOException e) {
            logger.warn("保存文件失败", e);
            throw new GenericAppException(SysErrorCode.FileSaveFailed, e);
        }
    }

    /**
     * 删除文件或目录，如果删除后目录为空，这空目录一并删除
     *
     * @param filename
     * @return
     */
    public static void delete(String filename) {
        logger.debug("删除文件：{}", filename);

        if (!StringUtils.hasText(filename)) {
            return;
        }

        Path path = Path.of(filename);
        if (!Files.exists(path)) {
            logger.info("要删除的文件不存在[{}]", filename);
            return;
        }

        try {
            Files.delete(path);
        } catch (IOException e) {
            logger.warn("删除文件失败[{}]", filename);
            throw new GenericAppException(SysErrorCode.FileDeleteFailed, e);
        }

        Path parent = path.getParent();
        if (parent == null) {
            return;
        }

        boolean empty = false;
        try (Stream<Path> entries = Files.list(parent)) {
            empty = entries.findFirst().isPresent();
        } catch (IOException e) {
        }

        if (empty) {
            try {
                Files.delete(parent);
            } catch (IOException e) {
                logger.warn("删除文件失败[{}]", filename);
                throw new GenericAppException(SysErrorCode.FileDeleteFailed, e);
            }
        }
    }
}
