package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysPersistentLogins.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysPersistentLogins
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysPersistentLogin;

public interface SysPersistentLoginsRepository extends
        PagingAndSortingRepository<SysPersistentLogin, String>, QuerydslPredicateExecutor<SysPersistentLogin> {
    long deleteByUid(Long uid);
}