package com.g.sys.prop;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_properties")
@IdClass(SysPropertyPK.class)
@DynamicInsert
@DynamicUpdate
public class SysProperty extends AbstractEntity<SysPropertyPK> {
    private String category;
    private String name;
    private String value;
    private String properties;
    private Integer sortOrder;
    private Status status;
    private String note;

    public SysProperty() {
    }

    public SysProperty(String category, String name) {
        this.category = category;
        this.name = name;
    }

    @Id
    @Column(name = "category")
    @NotEmpty
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Id
    @Column(name = "name")
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "value")
    @NotEmpty
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
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
        SysProperty that = (SysProperty) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysProperty{");
        sb.append("category='").append(category).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", properties='").append(properties).append('\'');
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", status=").append(status);
        sb.append(", note='").append(note).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Returns the id of the entity.
     *
     * @return the id. Can be {@literal null}.
     */
    @Override
    @Transient
    @JsonIgnore
    public SysPropertyPK getId() {
        return new SysPropertyPK(category, name);
    }
}
