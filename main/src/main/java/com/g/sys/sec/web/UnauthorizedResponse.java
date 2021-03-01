package com.g.sys.sec.web;

import com.g.commons.model.ApiResponse;

public class UnauthorizedResponse<T> extends ApiResponse<T> {
    public UnauthorizedResponse() {
        super("UNAUTHORIZED", "需要登录认证后才可访问指定的资源");
    }

    public UnauthorizedResponse(String message) {
        super("UNAUTHORIZED", message);
    }

    public UnauthorizedResponse(String result, String message, T body) {
        super("UNAUTHORIZED", message, body);
    }
}
