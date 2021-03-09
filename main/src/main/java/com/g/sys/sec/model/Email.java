package com.g.sys.sec.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Email {
    @NotEmpty(message = "电子邮箱不能为空")
    private String email;
}
