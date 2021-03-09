package com.g.commons.exception;

public class UnauthenticatedException extends GenericAppException {
    public UnauthenticatedException() {
        super(ErrorCode.Unauthenticated, ErrorMessage.Unauthenticated);
    }

    public UnauthenticatedException(String message) {
        super(ErrorCode.Unauthenticated, message);
    }
}
