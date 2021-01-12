package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_menu")
@DynamicInsert
@DynamicUpdate
public class SysMenu extends AbstractEntity {
    private Long id;
    private Long parentId;
    private String label;
    private String title;
    private String icon;
    private String url;
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
    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "label")
    @NotEmpty
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "title")
    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        SysMenu sysMenu = (SysMenu) o;
        return Objects.equals(id, sysMenu.id)
                && Objects.equals(parentId, sysMenu.parentId)
                && Objects.equals(label, sysMenu.label)
                && Objects.equals(title, sysMenu.title)
                && Objects.equals(icon, sysMenu.icon)
                && Objects.equals(url, sysMenu.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, label, title, icon, url);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysMenu{");
        sb.append("id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", label='").append(label).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
