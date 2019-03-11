package com.g.sys.sec.model;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

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
 * @since 2017-11-28
 */
@TableName("sys_users")
public class SysUser implements Serializable {
    private static final long serialVersionUID = -6511918582479158479L;

    public static final String UID = "uid";
    public static final String ACCOUNT = "account";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ENABLED = "enabled";

    @TableId(value = "uid", type = IdType.ID_WORKER)
    private Long uid;

    /**
     * 用户账号
     */
    @NotNull(message = "用户账号是必须的")
    @NotEmpty(message = "用户账号是必须的")
    @Size(max = 15, message = "用户账号最长为15个字符")
    private String account;

    /**
     * 用户名称
     */
    @NotNull(message = "用户名是必须的")
    @NotEmpty(message = "用户名是必须的")
    @Size(max = 50, message = "用户名最长为50个字符")
    private String username;

    private String password;

    @NotNull(message = "邮箱是必须的")
    @NotEmpty(message = "邮箱是必须的")
    @Size(max = 60, message = "邮箱最长为60个字符")
    private String email;

    private String enabled;

    @TableField(exist = false)
    private Set<String> roles;

    public Long getUid() {
        return uid;
    }

    public SysUser setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public SysUser setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SysUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SysUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEnabled() {
        return enabled;
    }

    public SysUser setEnabled(String enabled) {
        this.enabled = enabled;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysUser [");
        builder.append("uid=").append(uid).append(", ");
        builder.append("account=").append(account).append(", ");
        builder.append("username=").append(username).append(", ");
        builder.append("password=").append(password).append(", ");
        builder.append("email=").append(email).append(", ");
        builder.append("enabled=").append(enabled).append(", ");
        builder.append("roles=").append(roles);
        builder.append("]");
        return builder.toString();
    }
}
