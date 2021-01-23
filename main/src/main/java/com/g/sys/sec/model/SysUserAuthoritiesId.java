// Generated 2021年1月19日 上午11:12:12 by Hibernate Tools 6.0.0.Alpha2

package com.g.sys.sec.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SysUserAuthoritiesId generated by hbm2java
 */
@Embeddable
public class SysUserAuthoritiesId implements java.io.Serializable {

    private Long uid;
    private String authority;

    public SysUserAuthoritiesId() {
    }

    public SysUserAuthoritiesId(Long uid, String authority) {
        this.uid = uid;
        this.authority = authority;
    }

    @Column(name = "uid", nullable = false)
    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Column(name = "authority", nullable = false, length = 50)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean equals(Object other) {
        if ((this == other)) return true;
        if ((other == null)) return false;
        if (!(other instanceof SysUserAuthoritiesId)) return false;
        SysUserAuthoritiesId castOther = (SysUserAuthoritiesId) other;

        return ((this.getUid() == castOther.getUid()) || (this.getUid() != null && castOther.getUid() != null && this.getUid().equals(castOther.getUid())))
                && ((this.getAuthority() == castOther.getAuthority()) || (this.getAuthority() != null && castOther.getAuthority() != null && this.getAuthority().equals(castOther.getAuthority())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getUid() == null ? 0 : this.getUid().hashCode());
        result = 37 * result + (getAuthority() == null ? 0 : this.getAuthority().hashCode());
        return result;
    }
}

