package com.g.sys.dict.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 系统字典类型表
 * </p>
 *
 * @author Alfred Huang
 * @since 2017-07-18
 */
@TableName("sys_dict_category")
public class SysDictCategory implements Serializable {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String REMARK = "remark";
    public static final String STATE = "state";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 字典类型名
     */
    private String name;
    /**
     * 字典编码
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private String state;

    public Long getId() {
        return id;
    }

    public SysDictCategory setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SysDictCategory setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public SysDictCategory setCode(String code) {
        this.code = code;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SysDictCategory setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getState() {
        return state;
    }

    public SysDictCategory setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        return "SysDictCategory{" +
                "id=" + id +
                ", name=" + name +
                ", code=" + code +
                ", remark=" + remark +
                ", state=" + state +
                "}";
    }
}
