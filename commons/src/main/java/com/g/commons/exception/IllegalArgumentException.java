package com.g.commons.exception;

public class IllegalArgumentException extends GenericAppException {
    public IllegalArgumentException() {
        super(ErrorCode.IllegalArgument.code(), ErrorCode.IllegalArgument.message());
    }

    public IllegalArgumentException(String message) {
        super(ErrorCode.IllegalArgument.code(), message);
    }
}
