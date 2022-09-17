package com.g.sys.prop;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;

import com.g.commons.enums.Status;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_properties")
@DynamicInsert
@DynamicUpdate
@Cacheable
public class SysProperty extends AbstractEntity implements java.io.Serializable {
    private Long id;
    private String category;
    private String name;
    private String value;
    private String properties;
    private Integer sortOrder;
    private Status status;
    private String note;

    public SysProperty() {
    }

    public SysProperty(Long id) {
        this.id = id;
    }

    public SysProperty(String id) {
        this.id = Long.valueOf(id);
        ;
    }

    public SysProperty(Long id, String category, String name, String value, String properties, Integer sortOrder, Status status, String note) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.value = value;
        if (StringUtils.hasText(properties)) {
            this.properties = properties;
        }
        this.sortOrder = sortOrder;
        this.status = status;
        this.note = note;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(
            generator = "snowflake_generator"
    )
    @GenericGenerator(
            name = "snowflake_generator",
            strategy = "com.g.commons.utils.HibernateSnowflakeGenerator"
    )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "category")
    @NotEmpty
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
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
        if (StringUtils.hasText(properties)) {
            this.properties = properties;
        }
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
        sb.append("id=").append(id);
        sb.append(", category='").append(category).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", properties='").append(properties).append('\'');
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", status=").append(status);
        sb.append(", note='").append(note).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
