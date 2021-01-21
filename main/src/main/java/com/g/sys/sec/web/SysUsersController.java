package com.g.sys.sec.web;

/**
 * Controller for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysUsers
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysUsers;
import com.g.sys.sec.persistence.SysUsersRepository;
import com.g.sys.sec.service.SysUsersService;

@RestController
@RequestMapping("sys/users")
public class SysUsersController
        extends GenericController<SysUsersService, SysUsersRepository, SysUsers, Long> {
}