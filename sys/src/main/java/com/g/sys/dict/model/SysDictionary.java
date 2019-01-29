package com.g.sys.dict.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author Alfred Huang
 * @since 2017-07-18
 */
@TableName("sys_dictionary")
public class SysDictionary implements Serializable {

    public static final String ID = "id";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY = "category";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String PROPERTY_1 = "property_1";
    public static final String PROPERTY_2 = "property_2";
    public static final String PROPERTY_3 = "property_3";
    public static final String PROPERTY_4 = "property_4";
    public static final String STATE = "state";
    public static final String SORT_ORDER = "sort_order";
    public static final String NOTE = "note";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 字典类型ID
     */
    @TableField("category_id")
    private Long categoryId;
    /**
     * 字典类型
     */
    private String category;
    /**
     * 字典编码
     */
    private String code;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 属性1
     */
    @TableField("property_1")
    private String property1;
    /**
     * 属性2
     */
    @TableField("property_2")
    private String property2;
    /**
     * 属性3
     */
    @TableField("property_3")
    private String property3;
    /**
     * 属性4
     */
    @TableField("property_4")
    private String property4;
    /**
     * 字典状态
     */
    private String state;
    /**
     * 字典排序
     */
    @TableField("sort_order")
    private Integer sortOrder;
    /**
     * 字典备注
     */
    private String note;

    public Long getId() {
        return id;
    }

    public SysDictionary setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public SysDictionary setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public SysDictionary setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCode() {
        return code;
    }

    public SysDictionary setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public SysDictionary setName(String name) {
        this.name = name;
        return this;
    }

    public String getProperty1() {
        return property1;
    }

    public SysDictionary setProperty1(String property1) {
        this.property1 = property1;
        return this;
    }

    public String getProperty2() {
        return property2;
    }

    public SysDictionary setProperty2(String property2) {
        this.property2 = property2;
        return this;
    }

    public String getProperty3() {
        return property3;
    }

    public SysDictionary setProperty3(String property3) {
        this.property3 = property3;
        return this;
    }

    public String getProperty4() {
        return property4;
    }

    public SysDictionary setProperty4(String property4) {
        this.property4 = property4;
        return this;
    }

    public String getState() {
        return state;
    }

    public SysDictionary setState(String state) {
        this.state = state;
        return this;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public SysDictionary setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public String getNote() {
        return note;
    }

    public SysDictionary setNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public String toString() {
        return "SysDictionary{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", category=" + category +
                ", code=" + code +
                ", name=" + name +
                ", property1=" + property1 +
                ", property2=" + property2 +
                ", property3=" + property3 +
                ", property4=" + property4 +
                ", state=" + state +
                ", sortOrder=" + sortOrder +
                ", note=" + note +
                "}";
    }
}
