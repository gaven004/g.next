package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysAction.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysAction
 */

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysAction;

public interface SysActionRepository extends
        PagingAndSortingRepository<SysAction, Long>, QuerydslPredicateExecutor<SysAction> {
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select a from SysAction a where (a.resource = ?1) and (a.status = 'VALID')")
    Iterable<SysAction> findByResource(String resource);

    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select a from SysAction a where (a.resource like '%*%') and (a.status = 'VALID')")
    Iterable<SysAction> findByResourceWithWildcard();
}