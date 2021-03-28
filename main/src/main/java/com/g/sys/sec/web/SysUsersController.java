package com.g.sys.sec.web;

/**
 * Controller for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysUsers
 */

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.g.commons.exception.IllegalArgumentException;
import com.g.commons.model.AntdPageRequest;
import com.g.commons.model.AntdResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.ChangePasswordRequest;
import com.g.sys.sec.model.Email;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.persistence.SysUsersRepository;
import com.g.sys.sec.service.SysUsersService;

@RestController
@RequestMapping("sys/users")
public class SysUsersController
        extends GenericController<SysUsersService, SysUsersRepository, SysUser, Long> {
    @Override
    @GetMapping
    public AntdResponse<?> find(NativeWebRequest webRequest, AntdPageRequest pageRequest) throws Exception {
        AntdResponse<?> result = super.find(webRequest, pageRequest);
        Hibernate.initialize(result);
        return result;
    }

    @Override
    @GetMapping("/{id}")
    public AntdResponse<SysUser> get(@PathVariable Long id) {
        AntdResponse<SysUser> result = super.get(id);
        Hibernate.initialize(result);
        return result;
    }

    @PutMapping("/current-user")
    public AntdResponse<SysUser> updateCurrentUser(@RequestBody @Valid SysUser entity) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        if (!authUser.getId().equals(entity.getId())) {
            throw new IllegalArgumentException("只允许更新当前登录用户");
        }

        SysUser srcUser = service.get(authUser.getId());
        Hibernate.initialize(srcUser);
        entity.setRoles(srcUser.getRoles());
        return AntdResponse.success(service.update(entity));
    }

    @GetMapping("/current-user")
    public AntdResponse<SysUser> getCurrent() {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return get(authUser.getId());
    }

    @PutMapping("change-password")
    public AntdResponse<?> changePassword(@Validated @RequestBody ChangePasswordRequest request) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        service.changePassword(authUser.getId(), request.getOldPwd(), request.getNewPwd());
        return AntdResponse.success();
    }

    @PutMapping("reset-password")
    public AntdResponse<?> resetPassword(@Validated @RequestBody Email email) {
        service.resetPassword(email.getEmail());
        return AntdResponse.success();
    }
}