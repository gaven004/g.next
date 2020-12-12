package com.g.commons.exception;

import java.util.UUID;

/**
 * 通用系统异常
 * <p>
 * 每一个系统异常定义错误码，前台可以根据代码显示对应的错误信息，另外也便于定位错误的出处
 * <p>
 * code规则：前缀[CORE...] + 模块[] + 错误码
 *
 * @version 3.0, 2017-10-24, Gaven, 恢复message的格式，增加getDetail方法
 */
public class GenericAppException extends RuntimeException {
    public static final String DEFAULT_ERROR_CORE = "CORE-00-0000";
    public static final String DEFAULT_ERROR_MESSAGE = "App Exception";
    private static final long serialVersionUID = -1925535512076669332L;
    private String code;

    private String sn; // 异常唯一标识，用于日志跟踪

    public GenericAppException() {
        this(DEFAULT_ERROR_CORE, UUID.randomUUID().toString(), DEFAULT_ERROR_MESSAGE);
    }

    public GenericAppException(String message) {
        this(DEFAULT_ERROR_CORE, UUID.randomUUID().toString(), message);
    }

    public GenericAppException(String code, String message) {
        this(code, UUID.randomUUID().toString(), message);
    }

    public GenericAppException(String code, String sn, String message) {
        super(message);
        this.code = code;
        this.sn = sn;
    }

    public GenericAppException(String message, Throwable cause) {
        this(DEFAULT_ERROR_CORE, UUID.randomUUID().toString(), message, cause);
    }

    public GenericAppException(String code, String message, Throwable cause) {
        this(code, UUID.randomUUID().toString(), message, cause);
    }

    public GenericAppException(String code, String sn, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.sn = sn;
    }

    public String getDetail() {
        return String.format("%s, code: %s, sn: %s, message: %s", getClass().getName(), code, sn, getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getSn() {
        return sn;
    }
}
