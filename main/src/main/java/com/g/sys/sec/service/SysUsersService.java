package com.g.sys.sec.service;

/**
 * Service for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysUsers
 */

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import com.g.commons.service.GenericService;
import com.g.commons.utils.QuerydslBuilder;
import com.g.sys.sec.model.QSysUser;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.persistence.SysUsersRepository;

@Service
public class SysUsersService
        extends GenericService<SysUsersRepository, SysUser, Long> {
    static final String DEFAULT_PASSWORD = "888888";

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Querydsl querydsl;

    @Override
    @Transactional
    public SysUser get(Long id) {
        return super.get(id);
    }

    public SysUser findByAccount(String account) {
        return repository.findByAccount(account);
    }

    public SysUser findByEmail(String email) {
        return repository.findByAccount(email);
    }

    public Iterable<SysUser> findAllEssential(Predicate predicate, Sort sort) {
        QSysUser qSysUsers = QSysUser.sysUsers;
        JPQLQuery query = new JPAQuery(em);
        query.from(qSysUsers)
                .select(Projections.constructor(SysUser.class, qSysUsers.id, qSysUsers.account, qSysUsers.username))
                .where(predicate);
        return getQuerydsl().applySorting(sort, query).fetch();
    }

    @Override
    @Transactional
    public SysUser save(SysUser entity) {
        if (!StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        }
        return super.save(entity);
    }

    @Override
    @Transactional
    public SysUser update(SysUser entity) {
        if (StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        return super.update(entity);
    }

    private Querydsl getQuerydsl() {
        if (querydsl == null) {
            querydsl = QuerydslBuilder.build(SysUser.class, em);
        }

        return querydsl;
    }
}