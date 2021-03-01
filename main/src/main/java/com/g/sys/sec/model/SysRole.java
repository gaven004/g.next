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

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

/**
 * SysRoles generated by hbm2java
 */
@Entity
@Table(name = "sys_roles")
@DynamicInsert
@DynamicUpdate
public class SysRole extends AbstractEntity implements java.io.Serializable {

    private Long id;
    private String name;
    private Status status;

    private Set<SysAction> authorities = new HashSet<>();

    public SysRole() {
    }

    public SysRole(Long id) {
        this.id = id;
    }

    public SysRole(String id) {
        this.id = Long.valueOf(id);
    }

    public SysRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SysRole(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
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

    @Column(name = "name", nullable = false, length = 60)
    @NaturalId
    @NotEmpty
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
    @JoinTable(name = "sys_role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority")
    )
    @BatchSize(size=20)
    public Set<SysAction> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SysAction> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(SysAction action) {
        authorities.add(action);
    }

    public void removeAuthority(SysAction action) {
        authorities.remove(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole sysRoles = (SysRole) o;
        return Objects.equals(id, sysRoles.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysRole.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("status=" + status)
                .toString();
    }
}

