package com.g.commons.exception;

public class EntityNotFoundException extends GenericAppException {
    public EntityNotFoundException() {
        super(ErrorCode.EntityNotFound, ErrorMessage.EntityNotFound);
    }
}
