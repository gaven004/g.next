package com.g.sys.sec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

public class SysMenuDto {
    private String name;
    private String path;
    private String icon;
    private String component;
    private Menu menu;

    private List<SysMenuDto> routes;

    public SysMenuDto() {
    }

    public SysMenuDto(String name) {
        this.name = name;
    }

    public SysMenuDto(SysMenu menu) {
        this.name = menu.getId().toString();

        if (StringUtils.hasText(menu.getLabel()))
            this.menu = new Menu(menu.getLabel());

        if (StringUtils.hasText(menu.getIcon()))
            this.icon = menu.getIcon();

        if (StringUtils.hasText(menu.getUrl()))
            this.path = menu.getUrl();

        if (StringUtils.hasText(menu.getComponent()))
            this.component = menu.getComponent();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<SysMenuDto> getRoutes() {
        return routes;
    }

    public void setRoutes(List<SysMenuDto> routes) {
        this.routes = routes;
    }

    public void addRoute(SysMenuDto child) {
        if (routes == null) {
            routes = new ArrayList<>();
        }

        routes.add(child);
    }

    public void addRoute(SysMenu child) {
        addRoute(new SysMenuDto(child));
    }

    public void removeRoute(SysMenuDto child) {
        routes.remove(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMenuDto that = (SysMenuDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public class Menu {
        private String name;

        public Menu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
