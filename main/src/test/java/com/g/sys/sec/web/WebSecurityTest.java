package com.g.sys.sec.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

class WebSecurityTest {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Test
    void match() {
        assertTrue(pathMatcher.match("/sys/sec/action", "/sys/sec/action"));
        assertFalse(pathMatcher.match("/sys/sec/action/*", "/sys/sec/action"));
        assertTrue(pathMatcher.match("/sys/sec/action/*", "/sys/sec/action/1"));
        assertTrue(pathMatcher.match("/sys/sec/action/**", "/sys/sec/action"));
        assertTrue(pathMatcher.match("/sys/sec/action/**", "/sys/sec/action/1/2"));
    }

    @Test
    void compare() {
        final int count = 10000000;

        Long l = 398586992473931777L;
        String s = "438586992473931777";

        long t0, t1;

        t0 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            boolean b = s.equals(l.toString());
        }
        t1 = System.currentTimeMillis();

        System.out.println("String compare, elapse: " + (t1 - t0));

        t0 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            boolean b = Long.parseLong(s) == l.longValue();
        }
        t1 = System.currentTimeMillis();

        System.out.println("Long compare, elapse: " + (t1 - t0));
    }
}
