package com.g.commons.exception;

public class IllegalArgument extends GenericApiException {
    public static final String CODE = "ILLEGAL_ARGUMENT";

    public IllegalArgument() {
        super(CODE, "参数有误");
    }

    public IllegalArgument(String message) {
        super(CODE, message);
    }
}
