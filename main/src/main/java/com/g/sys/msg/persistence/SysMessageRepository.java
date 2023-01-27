package com.g.sys.msg.persistence;

/**
 * Repository for domain model class SysMessage.
 *
 * @author Hibernate Tools
 * @see com.g.sys.msg.persistence.SysMessage
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.msg.model.MessageStatus;
import com.g.sys.msg.model.SysMessage;

public interface SysMessageRepository extends
        PagingAndSortingRepository<SysMessage, Long>, QuerydslPredicateExecutor<SysMessage> {
    Page<SysMessage> findBySenderIdAndStatusAndSubjectLike(Pageable pageable, Long senderId, MessageStatus status, String subject);

    Page<SysMessage> findByRecipientsRecipientIdAndStatusAndSubjectLike(Pageable pageable, Long recipientId, MessageStatus status, String subject);
}
