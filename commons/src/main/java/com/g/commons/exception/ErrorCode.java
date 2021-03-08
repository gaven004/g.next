package com.g.commons.exception;

/**
 * 系统异常代码
 * <p>
 * code规则：前缀[CORE...] + 模块[] + 错误码
 *
 * @version 1.0, 2020-12-15, Gaven
 */

public final class ErrorCode {
    private ErrorCode() {
    }

    public static final String Generic = "CORE-00-0000";
    public static final String IllegalArgument = "CORE-00-0001";

    public static final String UnsupportedFileType = "CORE-00-0101";
    public static final String ExceedMaxSize = "CORE-00-0102";

    public static final String EntityNotFound = "CORE-00-0201";

    public static final String Unauthenticated = "SEC-00-0001";
}
