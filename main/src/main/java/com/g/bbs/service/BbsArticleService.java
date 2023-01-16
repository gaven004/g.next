package com.g.bbs.service;

/**
 * Service for domain model class BbsArticle.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.service.BbsArticle
 */

import org.springframework.stereotype.Service;

import com.g.bbs.model.BbsArticle;
import com.g.bbs.persistence.BbsArticleRepository;
import com.g.commons.service.GenericService;
import com.g.sys.att.model.Module;
import com.g.sys.att.service.SysAttachmentService;

@Service
public class BbsArticleService
        extends GenericService<BbsArticleRepository, BbsArticle, Long> {
    private final SysAttachmentService attachmentService;

    public BbsArticleService(SysAttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Override
    public BbsArticle save(BbsArticle entity) {
        entity = super.save(entity);
        attachmentService.save(Module.BBS_ATTACHMENT, entity.getId().toString(), entity.getFiles());
        return entity;
    }

    @Override
    public BbsArticle update(BbsArticle entity) {
        attachmentService.save(Module.BBS_ATTACHMENT, entity.getId().toString(), entity.getFiles());
        return super.update(entity);
    }

    @Override
    public void delete(Long id) {
        attachmentService.delete(Module.BBS_ATTACHMENT, id.toString());
        super.delete(id);
    }
}
