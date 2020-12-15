package com.g.sys.prop;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;

public class SysPropertiesPK implements Serializable {
    private String category;
    private String name;

    public SysPropertiesPK() {
    }

    public SysPropertiesPK(String category, String name) {
        this.category = category;
        this.name = name;
    }

    @Column(name = "category")
    @Id
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "name")
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysPropertiesPK that = (SysPropertiesPK) o;
        return Objects.equals(category, that.category) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name);
    }
}
