package com.g.commons.exception;

public class UnsupportedFileTypeException extends GenericAppException {
    private static final long serialVersionUID = -2222889008658819968L;

    public UnsupportedFileTypeException() {
        super("CORE-00-0101", "不支持的文件类型");
    }
}
