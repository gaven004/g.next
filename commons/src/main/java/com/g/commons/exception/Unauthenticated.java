package com.g.commons.exception;

public class Unauthenticated extends GenericAppException {
    public Unauthenticated() {
        super(ErrorCode.Unauthenticated, ErrorMessage.Unauthenticated);
    }

    public Unauthenticated(String message) {
        super(ErrorCode.Unauthenticated, message);
    }
}
