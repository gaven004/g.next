package com.g.sys.sec.model;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPwd;
    private String newPwd;
}
