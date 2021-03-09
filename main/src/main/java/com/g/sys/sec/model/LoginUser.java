package com.g.sys.sec.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginUser {
    @NotEmpty(message = "用户账号不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
