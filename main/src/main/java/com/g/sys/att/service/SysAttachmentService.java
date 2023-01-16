package com.g.sys.att.service;

/**
 * Service for domain model class SysAttachment.
 *
 * @author Hibernate Tools
 * @see com.g.sys.att.service.SysAttachment
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.g.commons.service.GenericService;
import com.g.sys.att.model.AttachmentFile;
import com.g.sys.att.model.Module;
import com.g.sys.att.model.SysAttachment;
import com.g.sys.att.persistence.SysAttachmentRepository;

@Service
public class SysAttachmentService
        extends GenericService<SysAttachmentRepository, SysAttachment, Long> {
    private final FileUploader uploader;

    public SysAttachmentService(FileUploader uploader) {
        this.uploader = uploader;
    }

    public FileUploader getUploader() {
        return uploader;
    }

    /**
     * 保存附件
     *
     * @param module    所属模块
     * @param srcRecode 主表记录编号
     * @param files     上传的文件信息。对应操作：<br>
     *                  新增：id为空，file不为空<br>
     *                  修改：id不为空，file不为空<br>
     *                  删除：id表示需要保留的附件，除此之外的附件全部删除，file为空（List为空表示删除所有附件）
     */
    @Transactional
    public void save(Module module, String srcRecode, List<AttachmentFile> files) {
        save(module, srcRecode, files, null, true, false);
    }

    @Transactional
    public void save(Module module, String srcRecode, List<AttachmentFile> files, String subPath, boolean genpath, boolean rename) {
        if (files == null || files.isEmpty()) {
            return;
        }

        // 删除无效数据
        files.removeIf(file -> file.getId() == null &&
                ((file.getFile() == null) ||
                        // 解决空文件的问题
                        (file.getFile().getSize() <= 0 && StringUtils.isEmpty(file.getFile().getOriginalFilename()))));

        // 查询原附件信息
        final Iterable<SysAttachment> original = repository.findByModuleAndSrcRecode(module, srcRecode);

        // 修改或添加操作，新增附件
        if (genpath) {
            // 忽略subPath和rename参数
            files.stream().filter(file -> file.getFile() != null && file.getFile().getSize() > 0)
                    .forEach(file -> save(module, srcRecode, file.getFile()));
        } else {
            if (rename) {
                files.stream().filter(file -> file.getFile() != null && file.getFile().getSize() > 0)
                        .forEach(file -> save(module, srcRecode, file.getFile(), subPath, true));
            } else {
                files.stream().filter(file -> file.getFile() != null && file.getFile().getSize() > 0)
                        .forEach(file -> {
                            if (StringUtils.isEmpty(file.getRename())) {
                                save(module, srcRecode, file.getFile(), subPath, false);
                            } else {
                                save(module, srcRecode, file.getFile(), subPath, file.getRename());
                            }
                        });
            }
        }

        // 修改或删除操作，删除原有附件
        original.forEach(att -> {
            boolean shouldDelete = true;
            for (AttachmentFile file : files) {
                if (att.getId().equals(file.getId())) {
                    if (file.getFile() == null || file.getFile().isEmpty()) {
                        // id相同，且上传文件为空，表示保留原有附件（不删除）
                        shouldDelete = false;
                    } else {
                        // id相同，上传文件不为空，表示替换附件（原附件需删除）
                        // 考虑到两次上传，可能是同一文件时，不做删除处理相对保险
                    }
                    break;
                }
            }
            if (shouldDelete) {
                delete(att.getId());
            }
        });
    }

    /**
     * 保存附件，保存路径：[basePath]/[modulePath]/[autoGenSubPath]/[sourceFileName]，自动生成子目录，保留原文件名
     *
     * @param module    所属模块
     * @param srcRecode 主表记录编号
     * @param file      上传的文件
     */
    @Transactional
    public void save(Module module, String srcRecode, MultipartFile file) {
        String path = uploader.uploadAndGetPath(file, module, true);
        _save(module, srcRecode, path);
    }

    /**
     * 保存附件，保存路径：[basePath]/[modulePath]/[subPath]/[rename]，指定保存的子目录及文件名
     *
     * @param module    所属模块
     * @param srcRecode 主表记录编号
     * @param file      上传的文件
     * @param subPath   文件保存的子目录
     * @param rename    文件名
     */
    @Transactional
    public void save(Module module, String srcRecode, MultipartFile file, String subPath, String rename) {
        String path = uploader.uploadAndGetPath(file, module, subPath, rename);
        _save(module, srcRecode, path);
    }

    /**
     * 保存附件，保存路径：[basePath]/[modulePath]/[subPath]/，自动重命名
     *
     * @param module
     * @param srcRecode
     * @param file
     * @param subPath
     * @param rename
     */
    @Transactional
    public void save(Module module, String srcRecode, MultipartFile file, String subPath, boolean rename) {
        String path = uploader.uploadAndGetPath(file, module, subPath, rename);
        _save(module, srcRecode, path);
    }

    private void _save(Module module, String srcRecode, String path) {
        if (path.startsWith(module.path())) {
            path = path.substring(module.path().length());
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        SysAttachment record = new SysAttachment();
        record.setModule(module);
        record.setSrcRecode(srcRecode);
        record.setPath(path);
        save(record);
    }

    /**
     * 删除附件
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        SysAttachment attachment = get(id);
        delete(attachment);
    }

    /**
     * 删除对应记录的全部附件
     *
     * @param module    所属模块
     * @param srcRecode 主表记录编号
     */
    @Transactional
    public void delete(Module module, String srcRecode) {
        final Iterable<SysAttachment> iterable = repository.findByModuleAndSrcRecode(module, srcRecode);
        iterable.forEach(this::delete);
    }

    /**
     * 删除附件
     */
    @Transactional
    public void delete(SysAttachment attachment) {
        if (attachment != null) {
            // 删除文件不可回滚，所以先删除数据库记录，如果删除数据库失败，则文件还在
            repository.deleteById(attachment.getId());
            uploader.delete(attachment.getModule(), attachment.getPath());
        }
    }

}
