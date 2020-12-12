package com.g.commons.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringUtilTest {
    @Test
    void printASCII() {
        for (char c = 33; c < 127; c++) {
            System.out.printf("'%c',\n", c);
        }
    }

    @Test
    void testRandomString() {
        for (int i = 0; i < 100; i++) {
            System.out.println(StringUtil.randomString(10, true, true, true, true));
        }
    }
}
