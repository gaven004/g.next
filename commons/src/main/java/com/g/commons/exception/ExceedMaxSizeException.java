package com.g.commons.exception;

public class ExceedMaxSizeException extends GenericAppException {
    private static final long serialVersionUID = 7657339572577017506L;

    public ExceedMaxSizeException() {
        super(ErrorCode.ExceedMaxSize.code(), ErrorCode.ExceedMaxSize.message());
    }

    public ExceedMaxSizeException(String message) {
        super(ErrorCode.ExceedMaxSize.code(), message);
    }
}
