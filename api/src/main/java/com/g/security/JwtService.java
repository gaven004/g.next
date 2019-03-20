package com.g.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtService implements InitializingBean {
    public static String ISSUER = "iss";
    public static String SUBJECT = "sub";
    public static String EXPIRES_AT = "exp";
    public static String ISSUED_AT = "iat";
    public static String TOKEN = "token";

    private String secret;
    private String issuer;
    private Long duration; // 有效期，单位：配置输入为秒，转为毫秒存储

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(secret, "[Assertion failed] - the secret argument must have text; it must not be null, empty, or blank");

        algorithm = Algorithm.HMAC256(secret);
        if (StringUtils.hasText(issuer)) {
            verifier = JWT.require(algorithm).withIssuer(issuer).build();
        } else {
            verifier = JWT.require(algorithm).build();
        }
    }

    public Map<String, Object> sign(Map<String, Object> payloads) {
        Assert.notNull(payloads);

        Map<String, Object> claims = new HashMap<>(payloads);

        long now = System.currentTimeMillis();
        final JWTCreator.Builder builder = JWT.create();

        if (StringUtils.hasText(issuer)) {
            builder.withIssuer(issuer);
            claims.put(ISSUER, issuer);
        }

        final Date issuedAt = new Date(now);
        builder.withIssuedAt(issuedAt);
        claims.put(ISSUED_AT, issuedAt);

        if (duration != null) {
            final Date expiresAt = new Date(now + duration);
            builder.withExpiresAt(expiresAt);
            claims.put(EXPIRES_AT, expiresAt);
        }

        payloads.forEach((key, value) -> {
            addClaim(builder, key, value);
            claims.put(key, value);
        });

        String token = builder.sign(algorithm);
        claims.put(TOKEN, token);

        return claims;
    }

    public Map<String, Claim> verify(String token) {
        Assert.hasText(token);
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public Map<String, Claim> decode(String token) {
        Assert.hasText(token);
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaims();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        if (StringUtils.hasText(secret)) {
            this.secret = secret;
        }
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        if (StringUtils.hasText(issuer)) {
            this.issuer = issuer;
        }
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        if (duration != null && duration > 0) {
            this.duration = duration * 1000;
        }
    }

    private void addClaim(JWTCreator.Builder builder, String key, Object value) {
        if (value instanceof String) {
            builder.withClaim(key, (String) value);
        } else if (value instanceof Integer) {
            builder.withClaim(key, (Integer) value);
        } else if (value instanceof Long) {
            builder.withClaim(key, (Long) value);
        } else if (value instanceof Double) {
            builder.withClaim(key, (Double) value);
        } else if (value instanceof Date) {
            builder.withClaim(key, (Date) value);
        } else if (value instanceof Boolean) {
            builder.withClaim(key, (Boolean) value);
        } else if (value instanceof String[]) {
            builder.withArrayClaim(key, (String[]) value);
        } else if (value instanceof Integer[]) {
            builder.withArrayClaim(key, (Integer[]) value);
        } else if (value instanceof Long[]) {
            builder.withArrayClaim(key, (Long[]) value);
        } else {
            throw new IllegalArgumentException("Unsupported value type");
        }
    }
}
