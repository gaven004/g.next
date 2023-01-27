package com.g.sys.msg.service;

/**
 * Service for domain model class SysMessage.
 *
 * @author Hibernate Tools
 * @see com.g.sys.msg.service.SysMessage
 */

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.utils.NullAwareBeanUtilsBean;
import com.g.sys.att.model.Module;
import com.g.sys.att.service.SysAttachmentService;
import com.g.sys.msg.model.MessageStatus;
import com.g.sys.msg.model.SysMessage;
import com.g.sys.msg.model.SysMessageRecipient;
import com.g.sys.msg.persistence.SysMessageRecipientRepository;
import com.g.sys.msg.persistence.SysMessageRepository;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.service.SysUsersService;
import com.querydsl.core.types.Predicate;

@Service
public class SysMessageService {
    private final SysMessageRepository messageRepository;

    private final SysMessageRecipientRepository recipientRepository;

    private final SysAttachmentService attachmentService;

    private final SysUsersService usersService;

    public SysMessageService(SysMessageRepository messageRepository,
                             SysMessageRecipientRepository recipientRepository,
                             SysAttachmentService attachmentService,
                             SysUsersService usersService) {
        this.messageRepository = messageRepository;
        this.recipientRepository = recipientRepository;
        this.attachmentService = attachmentService;
        this.usersService = usersService;
    }

    @Transactional
    public SysMessage save(SysMessage entity) {
        _setValue(entity);
        entity = messageRepository.save(entity);
        _saveEx(entity);
        return entity;
    }

    @Transactional
    public SysMessage update(SysMessage entity) {
        _setValue(entity);
        SysMessage dest = messageRepository.findById(entity.getId())
                .map(source -> {
                    try {
                        NullAwareBeanUtilsBean.getInstance().copyProperties(source, entity);
                    } catch (IllegalAccessException e) {
                        // Ignore
                    } catch (InvocationTargetException e) {
                        // Ignore
                    }
                    return messageRepository.save(source);
                })
                .orElseThrow(() -> new EntityNotFoundException());
        _saveEx(dest);
        return dest;
    }

    private static void _setValue(SysMessage entity) {
        if (MessageStatus.SENT.equals(entity.getStatus())) {
            entity.setStime(LocalDateTime.now());
        }

        if (entity.getFiles() != null) {
            // 删除无效数据
            entity.getFiles().removeIf(file -> file.getId() == null &&
                    ((file.getFile() == null) ||
                            // 解决空文件的问题
                            (file.getFile().getSize() <= 0 && StringUtils.isEmpty(file.getFile().getOriginalFilename()))));
            if (!entity.getFiles().isEmpty()) {
                entity.setHasAttachment((byte) 1);
            }
        }
    }

    /**
     * 保存收件人、附件等信息
     *
     * @param entity
     */
    private void _saveEx(SysMessage entity) {
        final Long messageId = entity.getId();

        final List<Long> target = entity.getRecipientId();
        if (target != null && !target.isEmpty()) {
            recipientRepository.deleteByMessageId(messageId);
            final List<SysMessageRecipient> recipientList = target.stream()
                    .map(recipientId -> {
                        final SysUser user = usersService.get(recipientId);
                        return new SysMessageRecipient(null, messageId, recipientId, user.getUsername());
                    })
                    .collect(Collectors.toList());
            recipientRepository.saveAll(recipientList);
        }

        if (entity.getFiles() != null) {
            attachmentService.save(Module.MSG_ATTACHMENT, messageId.toString(), entity.getFiles());
        }
    }

    @Transactional
    public void delete(Long id) {
        final Optional<SysMessage> optional = messageRepository.findById(id);
        SysMessage entity = optional.orElseThrow(EntityNotFoundException::new);
        entity.setStatus(MessageStatus.DELETED);
        messageRepository.save(entity);
    }

    public SysMessage get(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<SysMessage> findAll() {
        return messageRepository.findAll();
    }

    public Iterable<SysMessage> findAll(Predicate predicate) {
        return messageRepository.findAll(predicate);
    }

    public Iterable<SysMessage> findAll(Predicate predicate, Sort sort) {
        return messageRepository.findAll(predicate, sort);
    }

    public Page<SysMessage> findAll(Predicate predicate, Pageable pageable) {
        return messageRepository.findAll(predicate, pageable);
    }

    public Page<SysMessage> findDraft(Pageable pageable, Long senderId, String subject) {
        return messageRepository.findBySenderIdAndStatusAndSubjectLike(pageable, senderId, MessageStatus.DRAFT, subject);
    }

    public Page<SysMessage> findSent(Pageable pageable, Long senderId, String subject) {
        return messageRepository.findBySenderIdAndStatusAndSubjectLike(pageable, senderId, MessageStatus.SENT, subject);
    }

    public Page<SysMessage> findInbox(Pageable pageable, Long recipientId, String subject) {
        return messageRepository.findByRecipientsRecipientIdAndStatusAndSubjectLike(pageable, recipientId, MessageStatus.SENT, subject);
    }
}
