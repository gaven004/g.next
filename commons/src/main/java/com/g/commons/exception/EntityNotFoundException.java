package com.g.commons.exception;

public class EntityNotFoundException extends GenericAppException {
    public EntityNotFoundException() {
        super(ErrorCode.EntityNotFound, ErrorMessage.EntityNotFound);
    }

    public EntityNotFoundException(String message) {
        super(ErrorCode.EntityNotFound, message);
    }
}
