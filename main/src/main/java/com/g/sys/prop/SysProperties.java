package com.g.sys.prop;

import java.util.Objects;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_properties")
@IdClass(SysPropertiesPK.class)
@DynamicInsert
@DynamicUpdate
public class SysProperties extends AbstractEntity<SysPropertiesPK> {
    private String category;
    private String name;
    private String value;
    private String properties;
    private Integer sortOrder;
    private String status;
    private String note;

    public SysProperties() {
    }

    public SysProperties(String category, String name) {
        this.category = category;
        this.name = name;
    }

    @Id
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "properties")
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Basic
    @Column(name = "sort_order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysProperties that = (SysProperties) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, value);
    }

    /**
     * Returns the id of the entity.
     *
     * @return the id. Can be {@literal null}.
     */
    @Override
    @Transient
    @JsonIgnore
    public SysPropertiesPK getId() {
        return new SysPropertiesPK(category, name);
    }
}
