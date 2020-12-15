package com.g.commons.exception;

public class UnsupportedFileTypeException extends GenericAppException {
    private static final long serialVersionUID = -2222889008658819968L;

    public UnsupportedFileTypeException() {
        super(ErrorCode.UnsupportedFileType, ErrorMessage.UnsupportedFileType);
    }
}
