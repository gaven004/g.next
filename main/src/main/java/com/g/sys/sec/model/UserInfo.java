package com.g.sys.sec.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfo {
    private String account;
    private String username;
    private String token;
    private String issuer;
    private Date issuerAt;
    private Date expiresAt;
}
