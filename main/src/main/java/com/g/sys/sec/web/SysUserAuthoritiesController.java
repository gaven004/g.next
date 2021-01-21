package com.g.sys.sec.web;

/**
 * Controller for domain model class SysUserAuthorities.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysUserAuthorities
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysUserAuthorities;
import com.g.sys.sec.model.SysUserAuthoritiesId;
import com.g.sys.sec.persistence.SysUserAuthoritiesRepository;
import com.g.sys.sec.service.SysUserAuthoritiesService;

@RestController
@RequestMapping("sys/SysUserAuthorities")
public class SysUserAuthoritiesController
        extends GenericController<SysUserAuthoritiesService, SysUserAuthoritiesRepository, SysUserAuthorities, SysUserAuthoritiesId> {
}