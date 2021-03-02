package com.g.sys.sec.model;

import static com.g.commons.enums.Status.VALID;

import java.io.Serializable;
import java.util.*;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class SecurityUser implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Long id;
    private final String username; // Spring Security中，username对应S5系统中的account
    private final String nickname; // Spring Security中，nickname对应S5系统中的username
    private final String email;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private String password;

    public SecurityUser(SysUser usr) {
        this.id = usr.getId();
        this.username = usr.getAccount();
        this.nickname = usr.getUsername();
        this.password = usr.getPassword();
        this.email = usr.getEmail();
        this.enabled = VALID.equals(usr.getStatus());

        this.authorities = Collections.unmodifiableSet(buildAuthorities(usr.getRoles()));

        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
    }

    public SecurityUser(Long uid, String username, String nickname, String password, String email,
                        boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this(uid, username, nickname, password, email, authorities, true, true, true, enabled);
    }

    public SecurityUser(Long uid, String username, String nickname, String password, String email,
                        Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked,
                        boolean credentialsNonExpired, boolean enabled) {
        this.id = uid;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;

        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));

        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    private static SortedSet<GrantedAuthority> buildAuthorities(
            Set<SysRole> roles) {
        Assert.notNull(roles, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
                new AuthorityComparator());

        for (SysRole role : roles) {
            Assert.notNull(role, "Role list cannot contain any null elements");
            Assert.notNull(role.getAuthorities(), "Role list cannot contain any null authorities");

            if (VALID.equals(role.getStatus())) {
                for (SysAction authority : role.getAuthorities()) {
                    Assert.notNull(role, "GrantedAuthority list cannot contain any null elements");

                    if (VALID.equals(authority.getStatus())) {
                        sortedAuthorities.add(new SimpleGrantedAuthority(authority.getId().toString()));
                    }
                }
            }
        }

        return sortedAuthorities;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
                new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void eraseCredentials() {
        password = null;
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing
     * the same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof SecurityUser) {
            return id == (((SecurityUser) rhs).id);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Id: ").append(this.id).append("; ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Nickname: ").append(this.nickname).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Email: ").append(this.email).append("; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}
