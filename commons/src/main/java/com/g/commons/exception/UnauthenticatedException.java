package com.g.commons.exception;

public class UnauthenticatedException extends GenericAppException {
    public UnauthenticatedException() {
        super(ErrorCode.Unauthenticated.code(), ErrorCode.Unauthenticated.message());
    }

    public UnauthenticatedException(String message) {
        super(ErrorCode.Unauthenticated.code(), message);
    }
}
