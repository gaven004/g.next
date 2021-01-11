package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "sys_role_authorities")
@IdClass(SysRoleAuthoritiesPK.class)
public class SysRoleAuthorities {
    private Long roleId;
    private String authority;

    @Id
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleAuthorities that = (SysRoleAuthorities) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, authority);
    }
}
