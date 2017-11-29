package com.g.sys.sec.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 *
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@TableName("sys_persistent_logins")
public class SysPersistentLogins implements Serializable {
    private static final long serialVersionUID = -5029482496694740738L;

    private String uid;
    @TableId(value = "series", type = IdType.INPUT)
    private String series;
    private String token;
    @TableField("last_used")
    private Date lastUsed;

    public String getUid() {
        return uid;
    }

    public SysPersistentLogins setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public SysPersistentLogins setSeries(String series) {
        this.series = series;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SysPersistentLogins setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public SysPersistentLogins setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public static final String UID = "uid";
    public static final String SERIES = "series";
    public static final String TOKEN = "token";
    public static final String LAST_USED = "last_used";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysPersistentLogins [");
        builder.append("uid=").append(uid).append(", ");
        builder.append("series=").append(series).append(", ");
        builder.append("token=").append(token).append(", ");
        builder.append("lastUsed=").append(lastUsed);
        builder.append("]");
        return builder.toString();
    }
}
