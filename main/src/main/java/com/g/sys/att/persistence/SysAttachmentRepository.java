package com.g.sys.att.persistence;

/**
 * Repository for domain model class SysAttachment.
 *
 * @author Hibernate Tools
 * @see com.g.sys.att.persistence.SysAttachment
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.att.model.Module;
import com.g.sys.att.model.SysAttachment;

public interface SysAttachmentRepository extends
        PagingAndSortingRepository<SysAttachment, Long>, QuerydslPredicateExecutor<SysAttachment> {
    Iterable<SysAttachment> findByModuleAndSrcRecode(Module module, String srcRecode);
}
