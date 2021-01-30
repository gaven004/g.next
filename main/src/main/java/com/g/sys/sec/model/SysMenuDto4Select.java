package com.g.sys.sec.model;

import java.util.ArrayList;
import java.util.List;

public class SysMenuDto4Select {
    private String title;
    private Long value;
    private List<SysMenuDto4Select> children;

    public SysMenuDto4Select() {
    }

    public SysMenuDto4Select(String title, Long value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public List<SysMenuDto4Select> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuDto4Select> children) {
        this.children = children;
    }

    public void addChild(SysMenuDto4Select child) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(child);
    }
}
