package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysRoles.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysRoles
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysRoles;

public interface SysRolesRepository extends
        PagingAndSortingRepository<SysRoles, Long>, QuerydslPredicateExecutor<SysRoles> {
}