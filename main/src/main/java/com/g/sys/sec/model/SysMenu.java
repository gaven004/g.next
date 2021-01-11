package com.g.sys.sec.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "sys_menu")
public class SysMenu {
    private Long id;
    private Long parentId;
    private String label;
    private String title;
    private String icon;
    private String url;

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
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "title")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMenu sysMenu = (SysMenu) o;
        return Objects.equals(id, sysMenu.id) && Objects.equals(parentId, sysMenu.parentId) && Objects.equals(label, sysMenu.label) && Objects.equals(title, sysMenu.title) && Objects.equals(icon, sysMenu.icon) && Objects.equals(url, sysMenu.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, label, title, icon, url);
    }
}
