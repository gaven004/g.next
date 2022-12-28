package com.g.commons.exception;

/**
 * 系统异常代码
 * <p>
 * code规则：前缀[CORE...] + 模块[] + 错误码
 *
 * @version 1.0, 2020-12-15, Gaven
 */

public enum ErrorCode implements DescribableError {
    Generic("CORE-00-0000", "系统异常"),

    IllegalArgument("CORE-00-0001", "无效的参数"),

    UnsupportedFileType("CORE-00-0101", "不支持的文件类型"),
    ExceedMaxSize("CORE-00-0102", "文件超过大小限制"),

    EntityNotFound("CORE-00-0201", "没有指定的实体"),

    Unauthenticated("SEC-00-0001", "没有身份认证信息或认证信息已过期");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
