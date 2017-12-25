package com.g.sys.mc.exception;

import com.g.commons.exception.GenericAppException;

public class InvalidParentException extends GenericAppException {
    private static final long serialVersionUID = -7164909157250103766L;

    public InvalidParentException() {
        super("CORE-03-0101", "非法的父栏目");
    }
}
