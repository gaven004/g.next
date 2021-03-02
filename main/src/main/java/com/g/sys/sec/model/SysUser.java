// Generated 2021年1月19日 上午11:12:12 by Hibernate Tools 6.0.0.Alpha2

package com.g.sys.sec.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

/**
 * SysUsers generated by hbm2java
 */
@Entity
@Table(name = "sys_users", uniqueConstraints = @UniqueConstraint(columnNames = "account"))
@DynamicInsert
@DynamicUpdate
@Cacheable
public class SysUser extends AbstractEntity implements java.io.Serializable {

    private Long id;
    private String account;
    private String username;
    private String password;
    private String email;
    private Status status;

    private Set<SysRole> roles = new HashSet<>();

    public SysUser() {
    }

    public SysUser(Long id, String account, String username) {
        this.id = id;
        this.account = account;
        this.username = username;
    }

    public SysUser(Long id, String account, String username, Status status) {
        this.id = id;
        this.account = account;
        this.username = username;
        this.status = status;
    }

    public SysUser(Long id, String account, String username, String password, String email, Status status) {
        this.id = id;
        this.account = account;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(
            generator = "snowflake_generator"
    )
    @GenericGenerator(
            name = "snowflake_generator",
            strategy = "com.g.commons.utils.HibernateSnowflakeGenerator"
    )
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "account", unique = true, nullable = false, length = 15)
    @NaturalId
    @NotEmpty
    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "username", nullable = false, length = 50)
    @NotEmpty
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 60)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", length = 60)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "status", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @BatchSize(size=20)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public void addRole(SysRole role) {
        roles.add(role);
    }

    public void removeRole(SysRole role) {
        roles.remove(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser sysUsers = (SysUser) o;
        return Objects.equals(id, sysUsers.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysUser.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("account='" + account + "'")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("email='" + email + "'")
                .add("status=" + status)
                .toString();
    }
}
