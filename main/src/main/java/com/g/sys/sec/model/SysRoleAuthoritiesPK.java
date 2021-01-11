package com.g.sys.sec.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;

public class SysRoleAuthoritiesPK implements Serializable {
    private Long roleId;
    private String authority;

    @Column(name = "role_id")
    @Id
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "authority")
    @Id
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
        SysRoleAuthoritiesPK that = (SysRoleAuthoritiesPK) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, authority);
    }
}
