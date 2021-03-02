package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysUsers
 */

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysUser;

public interface SysUsersRepository extends
        PagingAndSortingRepository<SysUser, Long>, QuerydslPredicateExecutor<SysUser> {
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysUser findByAccount(String account);

    SysUser findByEmail(String email);
}