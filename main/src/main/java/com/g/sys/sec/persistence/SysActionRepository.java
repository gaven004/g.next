package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysAction.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysAction
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysAction;

public interface SysActionRepository extends
        PagingAndSortingRepository<SysAction, Long>, QuerydslPredicateExecutor<SysAction> {
}