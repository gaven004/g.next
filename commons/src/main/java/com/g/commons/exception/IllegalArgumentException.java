package com.g.commons.exception;

public class IllegalArgumentException extends GenericAppException {
    public IllegalArgumentException() {
        super(ErrorCode.IllegalArgument, ErrorMessage.IllegalArgument);
    }

    public IllegalArgumentException(String message) {
        super(ErrorCode.IllegalArgument, message);
    }
}
