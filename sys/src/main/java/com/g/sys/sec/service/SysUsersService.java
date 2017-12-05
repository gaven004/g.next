package com.g.sys.sec.service;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.g.commons.mail.MailSender;
import com.g.sys.sec.exception.DuplicateEmailException;
import com.g.sys.sec.exception.DuplicateUserAccountException;
import com.g.sys.sec.exception.PasswordMismatchException;
import com.g.sys.sec.exception.UserNotFoundException;
import com.g.sys.sec.mapper.SysUsersMapper;
import com.g.sys.sec.model.SysUser;

/**
 * <p>
 * 服务实现类
 *
 * 对于权限，使用另一个SERVICE操作，适用于有缓存的方式
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@Service
public class SysUsersService extends ServiceImpl<SysUsersMapper, SysUser> {
    private enum Action {
        NEW, RESET;
    }

    private static final Logger logger = LoggerFactory.getLogger(SysUsersService.class);

    private static final String REGISTER_MAIL_TEMPLATE = "com/g/sys/sec/template/reg.html";
    private static final String RESET_PASSWORD_MAIL_TEMPLATE = "com/g/sys/sec/template/reset-pwd.html";

    private static final String REGISTER_MAIL_SUBJECT = "新用户注册";
    private static final String RESET_PASSWORD_MAIL_SUBJECT = "密码重置";

    public static final int DEFAULT_PASSWORD_LENGTH = 8;

    @Autowired
    private SysAuthoritiesService sysAuthoritiesService;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    @Qualifier("usrMailSender")
    private MailSender mailSender;

    @Override
    public SysUser selectById(Serializable id) {
        SysUser user = super.selectById(id);
        if (user != null) {
            user.setRoles(sysAuthoritiesService.getAuthorities((String) id));
        }
        return user;
    }

    @Override
    public SysUser selectOne(Wrapper<SysUser> wrapper) {
        SysUser user = super.selectOne(wrapper);
        if (user != null) {
            user.setRoles(sysAuthoritiesService.getAuthorities(user.getUid()));
        }
        return user;
    }

    @Override
    public List<SysUser> selectList(Wrapper<SysUser> wrapper) {
        List<SysUser> list = super.selectList(wrapper);
        if (list != null && !list.isEmpty()) {
            for (SysUser user : list) {
                user.setRoles(sysAuthoritiesService.getAuthorities(user.getUid()));
            }
        }
        return list;
    }

    @Override
    public Page<SysUser> selectPage(Page<SysUser> page, Wrapper<SysUser> wrapper) {
        page = super.selectPage(page, wrapper);
        if (page.getRecords() != null && !page.getRecords().isEmpty()) {
            for (SysUser user : page.getRecords()) {
                user.setRoles(sysAuthoritiesService.getAuthorities(user.getUid()));
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(SysUser entity) {
        checkAccount(entity);
        checkEmail(entity);

        if (!StringUtils.hasText(entity.getPassword())) {
            String pwd = generatePwdAndSendMail(entity, Action.NEW);
            pwd = passwordEncoder.encode(pwd);
            entity.setPassword(pwd);
        }

        return retBool(baseMapper.insert(entity))
                && sysAuthoritiesService.insertAuthorities(entity.getUid(), entity.getRoles());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(SysUser entity) {
        if (entity.getAccount() != null) {
            checkAccount(entity);
        }

        if (entity.getEmail() != null) {
            checkEmail(entity);
        }

        if (entity.getRoles() != null) {
            sysAuthoritiesService.updateAuthorities(entity.getUid(), entity.getRoles());
        }

        return retBool(baseMapper.updateById(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteBatchIds(List<? extends Serializable> idList) {
        for (Serializable uid : idList) {
            sysAuthoritiesService.deleteAuthorities((String) uid);
        }
        return retBool(baseMapper.deleteBatchIds(idList));
    }

    /*
     * 检查重复账号
     */
    private void checkAccount(SysUser entity) throws DuplicateUserAccountException {
        Wrapper<SysUser> wrapper = new EntityWrapper<>();

        // 新增用户，用户账号不能与库中的记录重复
        wrapper.eq(SysUser.ACCOUNT, entity.getAccount());

        if (entity.getUid() != null) {
            // 旧用户修改，账号不能与除本记录外的其它记录重复
            wrapper.ne(SysUser.UID, entity.getUid());
        }

        if (selectCount(wrapper) > 0) {
            throw new DuplicateUserAccountException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(String uid, String oldpwd, String newpwd)
            throws UserNotFoundException, PasswordMismatchException {
        SysUser record = selectById(uid);
        if (record == null) {
            throw new UserNotFoundException();
        }

        if (!passwordEncoder.matches(oldpwd, record.getPassword())) {
            throw new PasswordMismatchException();
        }

        record = new SysUser();
        record.setUid(uid);
        record.setPassword(passwordEncoder.encode(newpwd));
        return retBool(baseMapper.updateById(record));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(String email) throws UserNotFoundException {
        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq(SysUser.EMAIL, email);

        List<SysUser> list = selectList(wrapper);
        if (list == null || list.isEmpty()) {
            throw new UserNotFoundException();
        }

        SysUser record = list.get(0);
        String uid = record.getUid();
        String pwd = generatePwdAndSendMail(record, Action.RESET);

        record = new SysUser();
        record.setUid(uid);
        record.setPassword(passwordEncoder.encode(pwd));
        return retBool(baseMapper.updateById(record));
    }

    /*
     * 检查重复邮箱
     */
    private void checkEmail(SysUser entity) throws DuplicateEmailException {
        Wrapper<SysUser> wrapper = new EntityWrapper<>();

        // 新增用户，用户邮箱不能与库中的记录重复
        wrapper.eq(SysUser.EMAIL, entity.getEmail());

        if (entity.getUid() != null) {
            // 旧用户修改，邮箱不能与除本记录外的其它记录重复
            wrapper.ne(SysUser.UID, entity.getUid());
        }

        if (selectCount(wrapper) > 0) {
            throw new DuplicateEmailException();
        }
    }

    private String generatePwdAndSendMail(SysUser entity, Action action) {
        String msg = null;
        String pwd = RandomStringUtils.randomAlphanumeric(DEFAULT_PASSWORD_LENGTH);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("account", entity.getAccount());
        model.put("username", entity.getUsername());
        model.put("password", pwd);

        switch (action) {
        case NEW:
            model.put("email", entity.getEmail());
            try {
                msg = composeHtml(REGISTER_MAIL_TEMPLATE, model);
                mailSender.sendHTMLMailAsync(entity.getEmail(), REGISTER_MAIL_SUBJECT, msg);
            } catch (IOException e) {
                logger.warn("发送注册邮件时系统异常", e);
            } catch (TemplateException e) {
                logger.warn("发送注册邮件时系统异常", e);
            }
            break;
        case RESET:
            try {
                msg = composeHtml(RESET_PASSWORD_MAIL_TEMPLATE, model);
                mailSender.sendHTMLMailAsync(entity.getEmail(), RESET_PASSWORD_MAIL_SUBJECT, msg);
            } catch (IOException e) {
                logger.warn("发送注册邮件时系统异常", e);
            } catch (TemplateException e) {
                logger.warn("发送注册邮件时系统异常", e);
            }
            break;
        }

        return pwd;
    }

    private String composeHtml(String template, Map<String, Object> model) throws IOException, TemplateException {
        return processTemplateIntoString(freemarkerConfiguration.getTemplate(template), model);
    }
}
