package com.g.sys.sec.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("sys_persistent_logins")
public class SysPersistentLogin implements Serializable {
    private static final long serialVersionUID = -5029482496694740738L;

    public static final String UID = "uid";
    public static final String SERIES = "series";
    public static final String TOKEN = "token";
    public static final String LAST_USED = "last_used";

    private Long uid;

    @TableId(value = "series", type = IdType.INPUT)
    private String series;

    private String token;

    @TableField("last_used")
    private Date lastUsed;

    public Long getUid() {
        return uid;
    }

    public SysPersistentLogin setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public SysPersistentLogin setSeries(String series) {
        this.series = series;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SysPersistentLogin setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public SysPersistentLogin setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysPersistentLogin [");
        builder.append("uid=").append(uid).append(", ");
        builder.append("series=").append(series).append(", ");
        builder.append("token=").append(token).append(", ");
        builder.append("lastUsed=").append(lastUsed);
        builder.append("]");
        return builder.toString();
    }
}
