package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysMenu.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysMenu
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysMenu;

public interface SysMenuRepository extends
        PagingAndSortingRepository<SysMenu, Long>, QuerydslPredicateExecutor<SysMenu> {
}