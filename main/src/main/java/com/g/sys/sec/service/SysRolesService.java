package com.g.sys.sec.service;

/**
 * Service for domain model class SysRoles.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysRoles
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysRoles;
import com.g.sys.sec.persistence.SysRolesRepository;

@Service
public class SysRolesService
        extends GenericService<SysRolesRepository, SysRoles, Long> {
}