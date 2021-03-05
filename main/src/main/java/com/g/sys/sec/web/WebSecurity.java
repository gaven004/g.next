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
        log.debug("Web Security Check, authentication: {}, request: {}", authentication, request);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null) {
            return false;
        }

        String method = request.getMethod().toUpperCase();
        String uri = request.getRequestURI();

        Iterable<SysAction> actions = actionService.findByResourceAndMethod(uri, method);

        if (actions == null) {
            // 简单处理，系统没登记的资源，则默认可自由访问
            return true;
        }

        Iterator<SysAction> iterator = actions.iterator();
        if (!iterator.hasNext()) {
            // 简单处理，系统没登记的资源，则默认可自由访问
            return true;
        }

        // TODO：若系统中登记的资源含通配符，则返回的actions结果不会为空，
        //  那没登记的资源，会在下面的处理中被拒绝，与原设想不符，
        //  导致所有的资源都需要登记，需要改进判断的逻辑

        while (iterator.hasNext()) {
            SysAction action = iterator.next();

            if (pathMatcher.match(action.getResource(), uri)) {
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
