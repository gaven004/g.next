package com.g.commons.exception;

import java.util.UUID;

/**
 * 通用系统异常
 * <p>
 * 每一个系统异常定义错误码，前台可以根据代码显示对应的错误信息，另外也便于定位错误的出处
 */
public class GenericApiException extends RuntimeException {
    private static final long serialVersionUID = -1925535512076669332L;

    public static final String CODE = "SYSTEM_EXCEPTION";

    private String code;
    private String sn; // 异常唯一标识，用于日志跟踪

    public GenericApiException() {
        this(CODE, UUID.randomUUID().toString(), "系统异常");
    }

    public GenericApiException(String message) {
        this(CODE, UUID.randomUUID().toString(), message);
    }

    public GenericApiException(String code, String message) {
        this(code, UUID.randomUUID().toString(), message);
    }

    public GenericApiException(String code, String sn, String message) {
        super(message);
        this.code = code;
        this.sn = sn;
    }

    public GenericApiException(String message, Throwable cause) {
        this(CODE, UUID.randomUUID().toString(), message, cause);
    }

    public GenericApiException(String code, String message, Throwable cause) {
        this(code, UUID.randomUUID().toString(), message, cause);
    }

    public GenericApiException(String code, String sn, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.sn = sn;
    }

    public String getDetail() {
        return String.format("code: %s, sn: %s, message: %s", code, sn, getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getSn() {
        return sn;
    }
}
