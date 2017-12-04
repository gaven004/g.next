package com.g.sys.sec.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@TableName("sys_authorities")
public class SysAuthorities implements Serializable {
    private static final long serialVersionUID = -8974936343023751543L;
    @TableId
    private String uid;
    @TableId
    private String authority;

    public String getUid() {
        return uid;
    }

    public SysAuthorities setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public SysAuthorities setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public static final String UID = "uid";
    public static final String AUTHORITY = "authority";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysAuthorities [");
        builder.append("uid=").append(uid).append(", ");
        builder.append("authority=").append(authority);
        builder.append("]");
        return builder.toString();
    }
}
