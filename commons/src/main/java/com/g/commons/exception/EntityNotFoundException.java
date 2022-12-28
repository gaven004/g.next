package com.g.commons.exception;

public class EntityNotFoundException extends GenericAppException {
    public EntityNotFoundException() {
        super(ErrorCode.EntityNotFound.code(), ErrorCode.EntityNotFound.message());
    }

    public EntityNotFoundException(String message) {
        super(ErrorCode.EntityNotFound.code(), message);
    }
}
