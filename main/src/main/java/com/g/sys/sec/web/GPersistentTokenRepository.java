package com.g.sys.sec.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.g.commons.utils.LocalDateTimeUtil;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.SysPersistentLogin;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.service.SysPersistentLoginsService;
import com.g.sys.sec.service.SysUsersService;

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
        record.setUid(user.getId());
        record.setToken(token.getTokenValue());
        record.setLastUsed(LocalDateTimeUtil.convertToLocalDateTime(token.getDate()));
        sysPersistentLoginsService.save(record);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        SysPersistentLogin record = new SysPersistentLogin();
        record.setSeries(series);
        record.setToken(tokenValue);
        record.setLastUsed(LocalDateTimeUtil.convertToLocalDateTime(lastUsed));
        sysPersistentLoginsService.save(record);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        SysPersistentLogin persistent = sysPersistentLoginsService.get(seriesId);

        if (persistent == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Querying token for series '" + seriesId + "' returned no results.");
            }

            return null;
        }

        SysUser user = sysUsersService.get(persistent.getUid());
        if (user == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Querying token for uid '" + persistent.getUid() + "' returned no results.");
            }

            return null;
        }

        return new PersistentRememberMeToken(user.getId().toString(), seriesId, persistent.getToken(),
                LocalDateTimeUtil.convertToDate(persistent.getLastUsed()));
    }

    @Override
    public void removeUserTokens(String username) {
        SysUser user = sysUsersService.findByAccount(username);
        if (user == null) {
            return;
        }

        sysPersistentLoginsService.deleteByUid(user.getId());
    }
}
