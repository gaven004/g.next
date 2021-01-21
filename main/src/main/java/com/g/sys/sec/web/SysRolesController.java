package com.g.sys.sec.web;

/**
 * Controller for domain model class SysRoles.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysRoles
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysRoles;
import com.g.sys.sec.persistence.SysRolesRepository;
import com.g.sys.sec.service.SysRolesService;

@RestController
@RequestMapping("sys/roles")
public class SysRolesController
        extends GenericController<SysRolesService, SysRolesRepository, SysRoles, Long> {
}