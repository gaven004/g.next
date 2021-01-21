package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysUserAuthorities.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysUserAuthorities
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysUserAuthorities;
import com.g.sys.sec.model.SysUserAuthoritiesId;

public interface SysUserAuthoritiesRepository extends
        PagingAndSortingRepository<SysUserAuthorities, SysUserAuthoritiesId>, QuerydslPredicateExecutor<SysUserAuthorities> {
}