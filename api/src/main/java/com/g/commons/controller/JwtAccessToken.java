package com.g.commons.controller;

import java.util.Date;

import lombok.Data;

@Data
public class JwtAccessToken {
    public static String Token_TYPE = "Bearer";

    private String username;
    private String token;
    private String issuer;
    private Date issuerAt;
    private Date expiresAt;
}
