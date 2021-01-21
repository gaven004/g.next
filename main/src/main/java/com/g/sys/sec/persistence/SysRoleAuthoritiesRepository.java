package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysRoleAuthorities.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysRoleAuthorities
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysRoleAuthorities;
import com.g.sys.sec.model.SysRoleAuthoritiesId;

public interface SysRoleAuthoritiesRepository extends
        PagingAndSortingRepository<SysRoleAuthorities, SysRoleAuthoritiesId>, QuerydslPredicateExecutor<SysRoleAuthorities> {
}