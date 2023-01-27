package com.g.sys.msg.persistence;

/**
 * Repository for domain model class SysMessageRecipient.
 *
 * @author Hibernate Tools
 * @see com.g.sys.msg.persistence.SysMessageRecipient
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.msg.model.SysMessageRecipient;

public interface SysMessageRecipientRepository extends
        PagingAndSortingRepository<SysMessageRecipient, Long>, QuerydslPredicateExecutor<SysMessageRecipient> {
    Iterable<SysMessageRecipient> findByMessageId(Long messageId);

    void deleteByMessageId(Long messageId);
}
