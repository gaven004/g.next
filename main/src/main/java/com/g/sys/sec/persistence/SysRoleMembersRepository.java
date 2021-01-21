package com.g.sys.sec.persistence;

/**
 * Repository for domain model class SysRoleMembers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.persistence.SysRoleMembers
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.sys.sec.model.SysRoleMembers;
import com.g.sys.sec.model.SysRoleMembersId;

public interface SysRoleMembersRepository extends
        PagingAndSortingRepository<SysRoleMembers, SysRoleMembersId>, QuerydslPredicateExecutor<SysRoleMembers> {
}