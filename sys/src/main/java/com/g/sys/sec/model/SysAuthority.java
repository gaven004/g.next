package com.g.sys.sec.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@TableName("sys_authorities")
public class SysAuthority implements Serializable {
    public static final String UID = "uid";
    public static final String AUTHORITY = "authority";
    private static final long serialVersionUID = -8974936343023751543L;
    @TableId
    private String uid;
    @TableId
    private String authority;

    public String getUid() {
        return uid;
    }

    public SysAuthority setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public SysAuthority setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysAuthority [");
        builder.append("uid=").append(uid).append(", ");
        builder.append("authority=").append(authority);
        builder.append("]");
        return builder.toString();
    }
}
