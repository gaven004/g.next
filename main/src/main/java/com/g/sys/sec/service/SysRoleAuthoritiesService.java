package com.g.sys.sec.service;

/**
 * Service for domain model class SysRoleAuthorities.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysRoleAuthorities
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysRoleAuthorities;
import com.g.sys.sec.model.SysRoleAuthoritiesId;
import com.g.sys.sec.persistence.SysRoleAuthoritiesRepository;

@Service
public class SysRoleAuthoritiesService
        extends GenericService<SysRoleAuthoritiesRepository, SysRoleAuthorities, SysRoleAuthoritiesId> {
}