package com.g.sys.msg.web;

/**
 * Controller for domain model class SysMessage.
 *
 * @author Hibernate Tools
 * @see com.g.sys.msg.web.SysMessage
 */

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.g.commons.exception.IllegalArgumentException;
import com.g.commons.model.ApiResponse;
import com.g.sys.msg.model.MessageStatus;
import com.g.sys.msg.model.SysMessage;
import com.g.sys.msg.service.SysMessageService;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.web.WebSecurityHelper;

@RestController
@RequestMapping("sys/message")
public class SysMessageController {
    private final SysMessageService service;

    public SysMessageController(SysMessageService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<?> find(@RequestParam String type, @RequestParam(required = false) String subject,
                               @PageableDefault Pageable pageable) {
        if (!StringUtils.hasText(subject)) {
            subject = "%";
        } else {
            subject = "%" + subject + "%";
        }

        SecurityUser currentUser = WebSecurityHelper.getAuthUser();

        if ("inbox".equalsIgnoreCase(type)) {
            return ApiResponse.success(service.findInbox(pageable, currentUser.getId(), subject));
        } else if ("sent".equalsIgnoreCase(type)) {
            return ApiResponse.success(service.findSent(pageable, currentUser.getId(), subject));
        } else if ("draft".equalsIgnoreCase(type)) {
            return ApiResponse.success(service.findDraft(pageable, currentUser.getId(), subject));
        }

        throw new IllegalArgumentException("不支持的查询类型");
    }

    @PostMapping
    public ApiResponse<SysMessage> save(SysMessage entity, @RequestParam String action) {
        SecurityUser currentUser = WebSecurityHelper.getAuthUser();
        entity.setSenderId(currentUser.getId());
        entity.setSenderName(currentUser.getNickname());
        if ("send".equalsIgnoreCase(action)) {
            entity.setStatus(MessageStatus.SENT);
        } else {
            entity.setStatus(MessageStatus.DRAFT);
        }

        return ApiResponse.success(service.save(entity));
    }

    @PatchMapping("/{id}")
    public ApiResponse<SysMessage> patch(SysMessage entity, @RequestParam String action) {
        SecurityUser currentUser = WebSecurityHelper.getAuthUser();
        entity.setSenderId(currentUser.getId());
        entity.setSenderName(currentUser.getNickname());
        if ("send".equalsIgnoreCase(action)) {
            entity.setStatus(MessageStatus.SENT);
        }
        return ApiResponse.success(service.update(entity));
    }
}
