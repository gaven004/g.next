package com.g.sys.sec.web;

/**
 * Controller for domain model class SysRoleAuthorities.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysRoleAuthorities
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysRoleAuthorities;
import com.g.sys.sec.model.SysRoleAuthoritiesId;
import com.g.sys.sec.persistence.SysRoleAuthoritiesRepository;
import com.g.sys.sec.service.SysRoleAuthoritiesService;

@RestController
@RequestMapping("sys/SysRoleAuthorities")
public class SysRoleAuthoritiesController
        extends GenericController<SysRoleAuthoritiesService, SysRoleAuthoritiesRepository, SysRoleAuthorities, SysRoleAuthoritiesId> {
}