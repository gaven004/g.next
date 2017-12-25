package com.g.commons.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.g.commons.exception.ExceedMaxSizeException;
import com.g.commons.exception.GenericAppException;
import com.g.commons.exception.UnsupportedFileTypeException;
import com.g.commons.utils.DateUtil;


public class UploadFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

    private static final String FILE_SEPARATOR = String.valueOf(File.separatorChar);

    private static final String URL_SEPARATOR = String.valueOf('/');

    private String uploadPath; // 上传目录

    private String browseUrl; // 浏览路径

    private long maxFileSize = 20 * 1024 * 1024; // 上传文件大小限制

    private HashSet<String> supportFileTypes;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        uploadPath = uploadPath.trim();
        if (uploadPath.charAt(uploadPath.length() - 1) == (File.separatorChar)) {
            this.uploadPath = uploadPath;
        } else {
            this.uploadPath = uploadPath + FILE_SEPARATOR;
        }
    }

    public String getBrowseUrl() {
        return browseUrl;
    }

    public void setBrowseUrl(String browseUrl) {
        browseUrl = browseUrl.trim();
        if (browseUrl.charAt(browseUrl.length() - 1) == '/') {
            this.browseUrl = browseUrl;
        } else {
            this.browseUrl = browseUrl + URL_SEPARATOR;
        }
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public HashSet<String> getSupportFileTypes() {
        return supportFileTypes;
    }

    public void setSupportFileTypes(HashSet<String> supportFileTypes) {
        if (this.supportFileTypes != null) {
            this.supportFileTypes.clear();
        } else {
            this.supportFileTypes = new HashSet<String>();
        }

        for (String type : supportFileTypes) {
            this.supportFileTypes.add(type.toLowerCase());
        }
    }

    /**
     * 保存上传文件到指定的目录，返回浏览的URL
     *
     * @param source
     * @return
     * @throws GenericAppException
     */
    public String upload(MultipartFile source) throws GenericAppException {
        String fileName = source.getOriginalFilename();
        if (!supportFileTypes.contains(FilenameUtils.getExtension(fileName).toLowerCase())) {
            throw new UnsupportedFileTypeException();
        }

        if (maxFileSize < source.getSize()) {
            throw new ExceedMaxSizeException();
        }

        // 上传目录
        String date = DateUtil.format(new Date(), DateUtil.DF_YYYYMMDD);
        String path = uploadPath + date;
        File directory = new File(path);
        if (!directory.exists() && !directory.isDirectory()) {
            if (!directory.mkdir()) {
                logger.error("无法创建上传目录");
                throw new GenericAppException("CORE-00-0103", "创建上传目录失败");
            }
        }

        path = path + FILE_SEPARATOR + fileName;
        File destination = new File(path);
        try {
            source.transferTo(destination);
        } catch (IllegalStateException | IOException e) {
            logger.error("无法写入上传文件", e);
            throw new GenericAppException("CORE-00-0104", "无法保存上传文件", e);
        }

        return browseUrl + date + URL_SEPARATOR + fileName;
    }
}
