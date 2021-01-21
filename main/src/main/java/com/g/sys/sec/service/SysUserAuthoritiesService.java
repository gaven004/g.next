package com.g.sys.sec.service;

/**
 * Service for domain model class SysUserAuthorities.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysUserAuthorities
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysUserAuthorities;
import com.g.sys.sec.model.SysUserAuthoritiesId;
import com.g.sys.sec.persistence.SysUserAuthoritiesRepository;

@Service
public class SysUserAuthoritiesService
        extends GenericService<SysUserAuthoritiesRepository, SysUserAuthorities, SysUserAuthoritiesId> {
}