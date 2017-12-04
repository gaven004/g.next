package com.g.sys.sec.exception;

import com.g.commons.exception.GenericAppException;

public class PasswordMismatchException extends GenericAppException {
    private static final long serialVersionUID = 2866620327522046778L;

    public PasswordMismatchException() {
        super("CORE-01-0104", "用户密码错误");
    }
}
