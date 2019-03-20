package com.g.commons.controller;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginUser {
    @NotNull(message = "用户账号不能为空")
    @NotEmpty(message = "用户账号不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空")
    private String password;
}
