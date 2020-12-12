package com.g.commons.exception;

import org.junit.jupiter.api.Test;

class GenericAppExceptionTest {
    @Test
    public void testNewException() {
        GenericAppException e = new GenericAppException();

        System.out.println("e = " + e);
        System.out.println("e = " + e.getDetail());
    }
}
