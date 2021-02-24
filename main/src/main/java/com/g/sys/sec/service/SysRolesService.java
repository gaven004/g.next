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
import com.g.sys.sec.model.QSysRoles;
import com.g.sys.sec.model.SysRoles;
import com.g.sys.sec.model.SysUsers;
import com.g.sys.sec.persistence.SysRolesRepository;

@Service
public class SysRolesService
        extends GenericService<SysRolesRepository, SysRoles, Long> {
    private Querydsl querydsl;

    public Iterable<SysRoles> findAllEssential(Predicate predicate, Sort sort) {
        QSysRoles qSysRoles = QSysRoles.sysRoles;
        JPQLQuery query = new JPAQuery(em);
        query.from(qSysRoles)
                .select(Projections.constructor(SysRoles.class, qSysRoles.id, qSysRoles.name))
                .where(predicate);
        return getQuerydsl().applySorting(sort, query).fetch();
    }

    private Querydsl getQuerydsl() {
        if (querydsl == null) {
            querydsl = QuerydslBuilder.build(SysRoles.class, em);
        }

        return querydsl;
    }
}