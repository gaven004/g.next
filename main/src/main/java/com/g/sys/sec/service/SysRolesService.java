package com.g.sys.sec.service;

/**
 * Service for domain model class SysRoles.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysRoles
 */

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import com.g.commons.service.GenericService;
import com.g.commons.utils.QuerydslBuilder;
import com.g.sys.sec.model.QSysRole;
import com.g.sys.sec.model.SysRole;
import com.g.sys.sec.persistence.SysRolesRepository;

@Service
public class SysRolesService
        extends GenericService<SysRolesRepository, SysRole, Long> {
    private Querydsl querydsl;

    public Iterable<SysRole> findAllEssential(Predicate predicate, Sort sort) {
        QSysRole qSysRoles = QSysRole.sysRoles;
        JPQLQuery query = new JPAQuery(em);
        query.from(qSysRoles)
                .select(Projections.constructor(SysRole.class, qSysRoles.id, qSysRoles.name))
                .where(predicate);
        return getQuerydsl().applySorting(sort, query).fetch();
    }

    private Querydsl getQuerydsl() {
        if (querydsl == null) {
            querydsl = QuerydslBuilder.build(SysRole.class, em);
        }

        return querydsl;
    }
}