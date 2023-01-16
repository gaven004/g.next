package com.g.sys.att.web;

/**
 * Controller for domain model class SysAttachment.
 *
 * @author Hibernate Tools
 * @see com.g.sys.att.web.SysAttachment
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.commons.web.ResponseUtil;
import com.g.sys.att.model.SysAttachment;
import com.g.sys.att.persistence.SysAttachmentRepository;
import com.g.sys.att.service.FileUploader;
import com.g.sys.att.service.SysAttachmentService;

@RestController
@RequestMapping("sys/attachment")
public class SysAttachmentController
        extends GenericController<SysAttachmentService, SysAttachmentRepository, SysAttachment, Long> {
    private static final Logger logger = LoggerFactory.getLogger(SysAttachmentController.class);

    @GetMapping("download/{id}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws IOException {
        final SysAttachment attachment = service.get(id);
        final FileUploader uploader = service.getUploader();

        Path path = uploader.getPath(attachment.getModule()).resolve(attachment.getPath());
        if (!Files.exists(path)) {
            logger.warn("请求的文件不存在[{}]", path);
            throw new RuntimeException("请求的文件不存在[" + path + "]");
        }

        ResponseUtil.write(request, response, path);
//        ResponseUtil.write(response, path);
    }
}
