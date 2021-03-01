package com.g.sys.sec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.SysUser;

@Service("gUserDetailsService")
public class GUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(GUserDetailsService.class);

    protected final MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();

    private final SysUsersService sysUsersService;

    @Autowired
    public GUserDetailsService(SysUsersService sysUsersService) {
        Assert.notNull(sysUsersService, "A SysUsersService must be set");
        this.sysUsersService = sysUsersService;
    }

    /**
     * 在Spring Security里，使用username作用User的PK
     * 但S5项目中，uid及account才是作为User的唯一标识，username则是中文的名称
     * 所以登录时，是使用account来登录的，这个与Security有所区别
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUsersService.findByAccount(username);
        if (user == null) {
            logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(messages.getMessage(
                    "JdbcDaoImpl.notFound", new Object[]{username},
                    "Username {0} not found"));
        }

        if (user.getRoles() == null || user.getRoles().size() == 0) {
            logger.debug("User '" + username
                    + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(messages.getMessage(
                    "JdbcDaoImpl.noAuthority", new Object[]{username},
                    "User {0} has no GrantedAuthority"));
        }

        return new SecurityUser(user);
    }

}
