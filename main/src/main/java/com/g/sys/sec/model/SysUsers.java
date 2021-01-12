package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_users")
@DynamicInsert
@DynamicUpdate
public class SysUsers extends AbstractEntity {
    private Long id;
    private String account;
    private String username;
    private String password;
    private String email;
    private Status status;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long uid) {
        this.id = uid;
    }

    @Basic
    @Column(name = "account")
    @NotEmpty
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "username")
    @NotEmpty
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
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUsers sysUsers = (SysUsers) o;
        return Objects.equals(id, sysUsers.id)
                && Objects.equals(account, sysUsers.account)
                && Objects.equals(username, sysUsers.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysUsers{");
        sb.append("id=").append(id);
        sb.append(", account='").append(account).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
