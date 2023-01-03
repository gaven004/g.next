package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysUsers
 */

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.g.sys.sec.model.SysUser;

public interface SysUsersRepository extends
        PagingAndSortingRepository<SysUser, Long>, QuerydslPredicateExecutor<SysUser> {
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysUser findByAccount(String account);

    SysUser findByEmail(String email);

    @Modifying
    @Query("update SysUser u set u.username = :username, u.email = :email where u.id = :id")
    void updateProfile(@Param(value = "id") long id, @Param(value = "username") String username, @Param(value = "email") String email);

    @Modifying
    @Query("update SysUser u set u.password = :password where u.id = :id")
    void updatePassword(@Param(value = "id") long id, @Param(value = "password") String password);
}
