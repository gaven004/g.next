package com.g.sys.sec.model;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "sys_persistent_logins")
public class SysPersistentLogins {
    private Long uid;
    private String series;
    private String token;
    private Timestamp lastUsed;

    @Basic
    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Id
    @Column(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "last_used")
    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysPersistentLogins that = (SysPersistentLogins) o;
        return Objects.equals(uid, that.uid) && Objects.equals(series, that.series) && Objects.equals(token, that.token) && Objects.equals(lastUsed, that.lastUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, series, token, lastUsed);
    }
}
