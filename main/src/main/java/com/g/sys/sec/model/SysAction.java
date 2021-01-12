package com.g.sys.sec.model;

import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_action")
@DynamicInsert
@DynamicUpdate
public class SysAction extends AbstractEntity {
    private Long id;
    private String resource;
    private ActionMethod method;
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
    @Column(name = "resource")
    @NotEmpty
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Basic
    @Column(name = "method")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    public ActionMethod getMethod() {
        return method;
    }

    public void setMethod(ActionMethod method) {
        this.method = method;
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
        SysAction sysAction = (SysAction) o;
        return id == sysAction.id
                && Objects.equals(resource, sysAction.resource)
                && Objects.equals(method, sysAction.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
