package com.g.commons.exception;

public final class ErrorMessage {
    private ErrorMessage() {
    }

    public static final String Generic = "系统异常";
    public static final String IllegalArgument = "无效的参数";

    public static final String UnsupportedFileType = "不支持的文件类型";
    public static final String ExceedMaxSize = "文件超过大小限制";

    public static final String EntityNotFound = "没有指定的实体";
}
