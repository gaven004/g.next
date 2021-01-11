package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "sys_user_authorities")
@IdClass(SysUserAuthoritiesPK.class)
public class SysUserAuthorities {
    private Long uid;
    private String authority;

    @Id
    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
        SysUserAuthorities that = (SysUserAuthorities) o;
        return Objects.equals(uid, that.uid) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, authority);
    }
}
