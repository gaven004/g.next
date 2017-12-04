package com.g.sys.sec.exception;

import com.g.commons.exception.GenericAppException;

public class DuplicateUserAccountException extends GenericAppException {
    private static final long serialVersionUID = -5539927034161531451L;

    public DuplicateUserAccountException() {
        super("CORE-01-0101", "重复的用户账号");
    }
}
