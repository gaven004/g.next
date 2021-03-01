package com.g.sys.sec.web;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private final String ip;

    /**
     * Constructor used for an authentication request. The {@link
     * org.springframework.security.core.Authentication#isAuthenticated()} will return
     * <code>false</code>.
     *
     * @param aPrincipal The pre-authenticated principal
     */
    public JwtAuthenticationToken(Object aPrincipal, String ip) {
        super(null);
        this.principal = aPrincipal;
        this.ip = ip;
    }

    /**
     * Constructor used for an authentication response. The {@link
     * org.springframework.security.core.Authentication#isAuthenticated()} will return
     * <code>true</code>.
     *
     * @param aPrincipal    The authenticated principal
     * @param anAuthorities The granted authorities
     */
    public JwtAuthenticationToken(Object aPrincipal, Object aCredentials,
                                  Collection<? extends GrantedAuthority> anAuthorities) {
        super(anAuthorities);
        this.principal = aPrincipal;
        this.ip = null;
        setAuthenticated(true);
    }

    /**
     * Get the credentials
     */
    public Object getCredentials() {
        return null;
    }

    /**
     * Get the principal
     */
    public Object getPrincipal() {
        return this.principal;
    }

    public String getIp() {
        return ip;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
