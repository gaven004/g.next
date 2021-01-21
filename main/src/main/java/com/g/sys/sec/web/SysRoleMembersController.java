package com.g.sys.sec.web;

/**
 * Controller for domain model class SysRoleMembers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysRoleMembers
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysRoleMembers;
import com.g.sys.sec.model.SysRoleMembersId;
import com.g.sys.sec.persistence.SysRoleMembersRepository;
import com.g.sys.sec.service.SysRoleMembersService;

@RestController
@RequestMapping("sys/SysRoleMembers")
public class SysRoleMembersController
        extends GenericController<SysRoleMembersService, SysRoleMembersRepository, SysRoleMembers, SysRoleMembersId> {
}