package com.g.commons.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

class IDGeneratorTest {
    IDGenerator generator = new IDGenerator(0, 0);

    @Test
    void getTS() throws ParseException {
        final Date date = DateUtil.parse("2020-01-01", DateTimePattern.DF_YYYY_MM_DD);
        System.out.println("date = " + date.getTime());
    }

    @Test
    void nextId() {
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.nextId());
        }
    }
}
