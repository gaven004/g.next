package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "sys_users")
public class SysUsers {
    private Long uid;
    private String account;
    private String username;
    private String password;
    private String email;
    private String status;

    @Id
    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUsers sysUsers = (SysUsers) o;
        return Objects.equals(uid, sysUsers.uid)
                && Objects.equals(account, sysUsers.account)
                && Objects.equals(username, sysUsers.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
