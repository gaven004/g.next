package com.g.sys.mc.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 系统文章栏目表
 * </p>
 *
 * @author Gaven
 * @since 2017-12-19
 */
@TableName("sys_column")
public class SysColumn implements Serializable {
    private static final long serialVersionUID = 7903918815117523629L;
    /**
     * 栏目ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 栏目名称
     */
    private String cname;
    /**
     * url地址标识
     */
    private String curl;
    /**
     * 父栏目
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 状态
     */
    private String state;

    public String getId() {
        return id;
    }

    public SysColumn setId(String id) {
        this.id = id;
        return this;
    }

    public String getCname() {
        return cname;
    }

    public SysColumn setCname(String cname) {
        this.cname = cname;
        return this;
    }

    public String getCurl() {
        return curl;
    }

    public SysColumn setCurl(String curl) {
        this.curl = curl;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public SysColumn setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getState() {
        return state;
    }

    public SysColumn setState(String state) {
        this.state = state;
        return this;
    }

    public static final String ID = "id";
    public static final String CNAME = "cname";
    public static final String CURL = "curl";
    public static final String PARENT_ID = "parent_id";
    public static final String STATE = "state";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysColumn [");
        builder.append("id=").append(id).append(", ");
        builder.append("cname=").append(cname).append(", ");
        builder.append("curl=").append(curl).append(", ");
        builder.append("parentId=").append(parentId).append(", ");
        builder.append("state=").append(state);
        builder.append("]");
        return builder.toString();
    }
}
