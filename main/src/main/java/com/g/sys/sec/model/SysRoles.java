package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "sys_roles")
public class SysRoles {
    private Long id;
    private String name;
    private String status;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        SysRoles sysRoles = (SysRoles) o;
        return Objects.equals(id, sysRoles.id)
                && Objects.equals(name, sysRoles.name)
                && Objects.equals(status, sysRoles.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
