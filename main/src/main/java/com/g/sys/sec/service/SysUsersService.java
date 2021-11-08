package com.g.sys.sec.service;

/**
 * Service for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysUsers
 */

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import freemarker.template.Configuration;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.exception.IllegalArgumentException;
import com.g.commons.service.GenericService;
import com.g.commons.utils.MailSender;
import com.g.commons.utils.QuerydslBuilder;
import com.g.commons.utils.StringUtil;
import com.g.sys.sec.model.QSysUser;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.persistence.SysUsersRepository;

@Service
public class SysUsersService
        extends GenericService<SysUsersRepository, SysUser, Long> {
    private static final Logger log = LoggerFactory.getLogger(SysUsersService.class);

    private static final String REGISTER_MAIL_TEMPLATE = "sys/sec/reg.html";
    private static final String RESET_PASSWORD_MAIL_TEMPLATE = "sys/sec/reset-pwd.html";

    private static final String REGISTER_MAIL_SUBJECT = "新用户注册";
    private static final String RESET_PASSWORD_MAIL_SUBJECT = "密码重置";

    static final String DEFAULT_PASSWORD = "888888";
    static final int PASSWORD_LENGTH = 6;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    @Qualifier("usrMailSender")
    private MailSender mailSender;

    private Querydsl querydsl;

    @Override
    @Transactional
    public SysUser get(Long id) {
        return super.get(id);
    }

    public SysUser findByAccount(@NotBlank String account) {
        return repository.findByAccount(account);
    }

    public SysUser findByEmail(@NotBlank String email) {
        return repository.findByEmail(email);
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
        checkAccount(entity);
        checkEmail(entity);
        if (!StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        }
        return super.save(entity);
    }

    @Override
    @Transactional
    public SysUser update(SysUser entity) {
        checkAccount(entity);
        checkEmail(entity);
        if (StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        return super.update(entity);
    }


    @Transactional
    public void changePassword(@NotNull Long id, @NotBlank String oldPwd, @NotBlank String newPwd) {
        SysUser user = get(id);
        if (user == null) {
            throw new EntityNotFoundException("系统中没有对应的用户");
        }

        if (!passwordEncoder.matches(oldPwd, user.getPassword())) {
            throw new IllegalArgumentException("原密码错误");
        }

        user.setPassword(passwordEncoder.encode(newPwd));
        super.update(user);
    }

    @Transactional
    public void resetPassword(@NotBlank String email) {
        SysUser user = findByEmail(email);

        if (user == null) {
            throw new EntityNotFoundException("系统中没有用户对应此电子邮箱");
        }

        log.info("用户（email：{}）重置密码", email);

        String pwd = StringUtil.randomString(PASSWORD_LENGTH, true, false, false, false);
        user.setPassword(passwordEncoder.encode(pwd));

        repository.save(user);

        sendMail(user, pwd, Action.RESET);
    }

    /*
     * 检查重复账号
     */
    private void checkAccount(SysUser src) {
        String account = src.getAccount();
        SysUser found = findByAccount(account);
        if (found != null && found.getId() != src.getId()) {
            throw new IllegalArgumentException("重复的用户账号");
        }
    }

    /*
     * 检查重复邮箱
     */
    private void checkEmail(SysUser src) {
        String email = src.getEmail();
        if (StringUtils.hasText(email)) {
            SysUser found = findByEmail(email);
            if (found != null && found.getId() != src.getId()) {
                throw new IllegalArgumentException("重复的用户账号");
            }
        }
    }

    private Querydsl getQuerydsl() {
        if (querydsl == null) {
            querydsl = QuerydslBuilder.build(SysUser.class, em);
        }

        return querydsl;
    }

    private void sendMail(SysUser entity, String password, Action action) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("account", entity.getAccount());
        model.put("username", entity.getUsername());
        model.put("password", password);

        switch (action) {
            case NEW:
                model.put("email", entity.getEmail());
                sendMail(entity, model, REGISTER_MAIL_TEMPLATE, REGISTER_MAIL_SUBJECT);
                break;
            case RESET:
                sendMail(entity, model, RESET_PASSWORD_MAIL_TEMPLATE, RESET_PASSWORD_MAIL_SUBJECT);
                break;
        }
    }

    private void sendMail(SysUser entity, Map<String, Object> model, String template, String subject) {
        String msg;
        try {
            msg = processTemplateIntoString(freemarkerConfiguration.getTemplate(template), model);
            mailSender.sendHTMLMailAsync(entity.getEmail(), subject, msg);
        } catch (Exception e) {
            log.warn("发送注册邮件时系统异常", e);
        }
    }

    private enum Action {
        NEW, RESET
    }
}