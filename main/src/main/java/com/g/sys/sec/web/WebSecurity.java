package com.g.sys.sec.web;

import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import com.g.sys.sec.model.ActionMethod;
import com.g.sys.sec.model.SysAction;
import com.g.sys.sec.service.SysActionService;

@Service
public class WebSecurity {
    private static final Logger log = LoggerFactory.getLogger(WebSecurity.class);

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final SysActionService actionService;

    @Autowired
    public WebSecurity(SysActionService actionService) {
        Assert.notNull(actionService, "A SysActionService must be set");

        this.actionService = actionService;
    }

    public boolean check(Authentication authentication, HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String uri = request.getRequestURI();

        log.debug("Web Security Check, authentication: {}, request: {} {}", authentication, method, uri);

        ActionMethod actionMethod = null;
        try {
            actionMethod = ActionMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return check(authentication, uri, actionMethod);
    }

    public boolean check(String uri) {
        try {
            final Authentication authentication = WebSecurityHelper.getAuthentication();
            return check(authentication, uri, ActionMethod.GET);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean check(Authentication authentication, String uri) {
        return check(authentication, uri, ActionMethod.GET);
    }

    public boolean check(Authentication authentication, String uri, ActionMethod actionMethod) {
        if ("admin".equals(authentication.getName())) {
            return true;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null) {
            return false;
        }

        // 先做精确匹配
        Iterator<SysAction> iterator = actionService.findByResource(uri).iterator();
        if (check(authorities, uri, actionMethod, iterator)) return true;

        // 再做通配符匹配
        iterator = actionService.findByResourceWithWildcard().iterator();
        if (check(authorities, uri, actionMethod, iterator)) return true;

        return false;
    }

    public boolean check(Collection<? extends GrantedAuthority> authorities, String uri, ActionMethod actionMethod, Iterator<SysAction> iterator) {
        while (iterator.hasNext()) {
            SysAction action = iterator.next();

            if (pathMatcher.match(action.getResource(), uri) &&
                    (ActionMethod.ALL.equals(action.getMethod()) ||
                            actionMethod == null ||
                            actionMethod.equals(action.getMethod()))) {
                if (action.getPermitAll()) {
                    return true;
                }

                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals(action.getId().toString())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
