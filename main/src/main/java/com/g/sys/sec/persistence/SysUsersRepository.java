package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysUsers
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysUsers;

public interface SysUsersRepository extends
        PagingAndSortingRepository<SysUsers, Long>, QuerydslPredicateExecutor<SysUsers> {
}