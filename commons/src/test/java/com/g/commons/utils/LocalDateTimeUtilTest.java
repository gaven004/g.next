package com.g.commons.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class LocalDateTimeUtilTest {

    @Test
    void format() {
        final LocalDateTime now = LocalDateTime.now();
        System.out.println(LocalDateTimeUtil.format(now, DateTimePattern.DF_YYYY_MM_DD_HH_MM_SS));
    }

    @Test
    void parse() throws ParseException {
        String s = "2020-12-12 16:25:57";
        LocalDateTime localDateTime = LocalDateTimeUtil.parse(s, DateTimePattern.DF_YYYY_MM_DD_HH_MM_SS);
        System.out.println("localDateTime = " + localDateTime);
    }
}
