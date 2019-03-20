package org.springframework.security.authentication;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;

import com.g.security.JwtService;
import com.g.security.PayloadKey;

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private JwtService jwtService;

    private UserDetailsService userDetailsService;

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(JwtAuthenticationToken.class, authentication, "Only JwtAuthenticationToken is supported");
        Assert.notNull(authentication.getPrincipal(), "Token is null");

        // Determine token
        String token = authentication.getName();
        logger.debug("Token to authenticate: " + token);

        String username = null;
        try {
            final Map<String, Claim> claims = jwtService.verify(token);
            preAuthenticationChecks(claims, (JwtAuthenticationToken) authentication);
            username = claims.get(PayloadKey.username).asString();
        } catch (JWTVerificationException fail) {
            throw new InsufficientAuthenticationException(fail.getMessage());
        }

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(username, "");
        return super.authenticate(userToken);
    }

    protected void preAuthenticationChecks(Map<String, Claim> claims, JwtAuthenticationToken authentication) {
        if (StringUtils.hasText(authentication.getIp()) && claims.get("ip") != null) {
            if (!authentication.getIp().equals(claims.get(PayloadKey.ip).asString())) {
                logger.warn("IP has changed, login ip: " + claims.get("ip").asString() + " request ip: " + authentication.getIp());
                throw new InsufficientAuthenticationException("IP has changed");
            }
        }
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        // do nothing
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
}
