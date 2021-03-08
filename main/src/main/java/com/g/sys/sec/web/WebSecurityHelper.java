package com.g.sys.sec.web;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.g.commons.exception.Unauthenticated;
import com.g.sys.sec.model.SecurityUser;

public class WebSecurityHelper {
    public static SecurityUser getAuthUser() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(context -> context.getAuthentication())
                .map(authentication -> (SecurityUser) authentication.getPrincipal())
                .orElseThrow(() -> new Unauthenticated());
    }

    public static Authentication getAuthentication() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(context -> context.getAuthentication())
                .orElseThrow(() -> new Unauthenticated());
    }
}
