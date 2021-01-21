package com.g.sys.sec.service;

/**
 * Service for domain model class SysRoleMembers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysRoleMembers
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysRoleMembers;
import com.g.sys.sec.model.SysRoleMembersId;
import com.g.sys.sec.persistence.SysRoleMembersRepository;

@Service
public class SysRoleMembersService
        extends GenericService<SysRoleMembersRepository, SysRoleMembers, SysRoleMembersId> {
}