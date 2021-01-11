package com.g.sys.sec.model;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "sys_action")
public class SysAction {
    private long id;
    private String resource;
    private Object method;
    private Object status;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "resource")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Basic
    @Column(name = "method")
    public Object getMethod() {
        return method;
    }

    public void setMethod(Object method) {
        this.method = method;
    }

    @Basic
    @Column(name = "status")
    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysAction sysAction = (SysAction) o;
        return id == sysAction.id && Objects.equals(resource, sysAction.resource) && Objects.equals(method, sysAction.method) && Objects.equals(status, sysAction.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resource, method, status);
    }
}
