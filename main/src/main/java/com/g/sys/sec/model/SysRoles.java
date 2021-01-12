package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_roles")
@DynamicInsert
@DynamicUpdate
public class SysRoles extends AbstractEntity {
    private Long id;
    private String name;
    private Status status;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        SysRoles sysRoles = (SysRoles) o;
        return Objects.equals(id, sysRoles.id)
                && Objects.equals(name, sysRoles.name)
                && Objects.equals(status, sysRoles.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysRoles{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
