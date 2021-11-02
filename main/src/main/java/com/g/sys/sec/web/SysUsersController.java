package com.g.sys.sec.web;

/**
 * Controller for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysUsers
 */

import javax.validation.Valid;

import com.g.commons.model.ApiResponse;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.g.commons.exception.IllegalArgumentException;
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
    public ApiResponse<?> find(NativeWebRequest webRequest, @PageableDefault Pageable pageable) throws Exception {
        ApiResponse<?> result = super.find(webRequest, pageable);
        Hibernate.initialize(result);
        return result;
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<SysUser> get(@PathVariable Long id) {
        ApiResponse<SysUser> result = super.get(id);
        Hibernate.initialize(result);
        return result;
    }

    @PutMapping("/current-user")
    public ApiResponse<SysUser> updateCurrentUser(@RequestBody @Valid SysUser entity) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        if (!authUser.getId().equals(entity.getId())) {
            throw new IllegalArgumentException("只允许更新当前登录用户");
        }

        SysUser srcUser = service.get(authUser.getId());
        Hibernate.initialize(srcUser);
        entity.setRoles(srcUser.getRoles());
        return ApiResponse.success(service.update(entity));
    }

    @GetMapping("/current-user")
    public ApiResponse<SysUser> getCurrent() {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return get(authUser.getId());
    }

    @PutMapping("change-password")
    public ApiResponse<?> changePassword(@Validated @RequestBody ChangePasswordRequest request) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        service.changePassword(authUser.getId(), request.getOldPwd(), request.getNewPwd());
        return ApiResponse.success();
    }

    @PutMapping("reset-password")
    public ApiResponse<?> resetPassword(@Validated @RequestBody Email email) {
        service.resetPassword(email.getEmail());
        return ApiResponse.success();
    }
}