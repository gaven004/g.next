package com.g.sys.sec.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;

public class SysUserAuthoritiesPK implements Serializable {
    private Long uid;
    private String authority;

    @Column(name = "uid")
    @Id
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
        SysUserAuthoritiesPK that = (SysUserAuthoritiesPK) o;
        return Objects.equals(uid, that.uid) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, authority);
    }
}
