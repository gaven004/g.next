package com.g.sys.sec.exception;

import com.g.commons.exception.GenericAppException;

public class DuplicateEmailException extends GenericAppException {
    private static final long serialVersionUID = -193223148958851229L;

    public DuplicateEmailException() {
        super("CORE-01-0102", "重复的用户邮箱");
    }
}
