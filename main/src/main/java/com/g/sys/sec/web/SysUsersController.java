package com.g.sys.sec.web;

/**
 * Controller for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysUsers
 */

import org.hibernate.Hibernate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.g.commons.model.AntdPageRequest;
import com.g.commons.model.AntdResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.Email;
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

    @PutMapping("reset-password")
    public AntdResponse<?> resetPassword(@Validated @RequestBody Email email) {
        service.resetPassword(email.getEmail());
        return AntdResponse.success();
    }
}