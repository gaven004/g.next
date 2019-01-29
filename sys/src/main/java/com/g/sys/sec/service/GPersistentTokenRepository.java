package com.g.sys.sec.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.SysPersistentLogin;
import com.g.sys.sec.model.SysUser;

public class GPersistentTokenRepository implements PersistentTokenRepository {
    private static final Logger logger = LoggerFactory.getLogger(GPersistentTokenRepository.class);

    @Autowired
    private SysPersistentLoginsService sysPersistentLoginsService;

    @Autowired
    private SysUsersService sysUsersService;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysPersistentLogin record = new SysPersistentLogin();
        record.setSeries(token.getSeries());
        record.setUid(user.getUid());
        record.setToken(token.getTokenValue());
        record.setLastUsed(token.getDate());
        sysPersistentLoginsService.save(record);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        SysPersistentLogin record = new SysPersistentLogin();
        record.setSeries(series);
        record.setToken(tokenValue);
        record.setLastUsed(lastUsed);
        sysPersistentLoginsService.updateById(record);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        SysPersistentLogin persistent = sysPersistentLoginsService.getById(seriesId);

        if (persistent == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Querying token for series '" + seriesId + "' returned no results.");
            }

            return null;
        }

        SysUser user = sysUsersService.getById(persistent.getUid());
        if (user == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Querying token for uid '" + persistent.getUid() + "' returned no results.");
            }

            return null;
        }

        return new PersistentRememberMeToken(user.getUid(), seriesId, persistent.getToken(),
                persistent.getLastUsed());
    }

    @Override
    public void removeUserTokens(String username) {
        QueryWrapper wrapper = new QueryWrapper<SysUser>();
        wrapper.eq(SysUser.ACCOUNT, username);

        SysUser user = sysUsersService.getOne(wrapper);
        if (user == null) {
            return;
        }

        wrapper = new QueryWrapper<SysPersistentLogin>();
        wrapper.eq(SysPersistentLogin.UID, user.getUid());

        sysPersistentLoginsService.remove(wrapper);
    }
}
