package com.g.sys.sec.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 *
 * </p>
 *
 * @author Gaven
 * @since 2017-11-28
 */
@TableName("sys_users")
public class SysUsers implements Serializable {
    private static final long serialVersionUID = -6511918582479158479L;

    @TableId(value = "uid", type = IdType.INPUT)
    private String uid;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户名称
     */
    private String username;
    private String password;
    private String email;
    private String enabled;

    public String getUid() {
        return uid;
    }

    public SysUsers setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public SysUsers setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SysUsers setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUsers setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SysUsers setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEnabled() {
        return enabled;
    }

    public SysUsers setEnabled(String enabled) {
        this.enabled = enabled;
        return this;
    }

    public static final String UID = "uid";
    public static final String ACCOUNT = "account";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ENABLED = "enabled";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysUsers [");
        builder.append("uid=").append(uid).append(", ");
        builder.append("account=").append(account).append(", ");
        builder.append("username=").append(username).append(", ");
        builder.append("password=").append(password).append(", ");
        builder.append("email=").append(email).append(", ");
        builder.append("enabled=").append(enabled);
        builder.append("]");
        return builder.toString();
    }
}
