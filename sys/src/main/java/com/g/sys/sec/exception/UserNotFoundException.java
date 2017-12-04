package com.g.sys.sec.exception;

import com.g.commons.exception.GenericAppException;

public class UserNotFoundException extends GenericAppException {
    private static final long serialVersionUID = -4602426655412822639L;

    public UserNotFoundException() {
        super("CORE-01-0103", "对应的用户不存在");
    }
}
