package com.g.commons.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class HexIDGeneratorTest {
    HexIDGenerator generator = new HexIDGenerator("00");

    @Test
    void testShort() {
        short i = (short) (Short.MAX_VALUE + 1);
        System.out.printf("i = %d, %s\n", i, Integer.toHexString(i));
        System.out.printf("toUnsignedInt = %d, %s\n", Short.toUnsignedInt(i), Integer.toHexString(Short.toUnsignedInt(i)));

        i++;
        System.out.printf("i = %d, %s\n", i, Integer.toHexString(i));
        System.out.printf("toUnsignedInt = %d, %s\n", Short.toUnsignedInt(i), Integer.toHexString(Short.toUnsignedInt(i)));

        i = -1;
        System.out.printf("i = %d, %s\n", i, Integer.toHexString(i));
        System.out.printf("toUnsignedInt = %d, %s\n", Short.toUnsignedInt(i), Integer.toHexString(Short.toUnsignedInt(i)));

        i++;
        System.out.printf("i = %d, %s\n", i, Integer.toHexString(i));
        System.out.printf("toUnsignedInt = %d, %s\n", Short.toUnsignedInt(i), Integer.toHexString(Short.toUnsignedInt(i)));
    }

    @Test
    void testNewLocalDateTime() {
        long ts = System.currentTimeMillis();

        Date d = new Date(ts);
        String ds = DateUtil.format(d, DateTimePattern.DF_YYYY_MM_DD_HH_MM_SS);
        System.out.println("ds = " + ds);

        ZoneOffset offset = OffsetDateTime.now().getOffset();

        LocalDateTime ld = LocalDateTime.ofEpochSecond(ts / 1000, 0, offset);
        String lds = LocalDateTimeUtil.format(ld, DateTimePattern.DF_YYYY_MM_DD_HH_MM_SS);
        System.out.println("lds = " + lds);

        assertEquals(ds, lds);
    }

    @Test
    void nextId() {
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.nextId());
        }
    }

    @Test
    void duplicate() {
        final int maxValue = Integer.MAX_VALUE >> 8;

        Set<String> set = new HashSet<>(maxValue);

        long start = System.currentTimeMillis();

        for (int i = 0; i < maxValue; i++) {
            assertTrue(set.add(generator.nextId()));
        }

        long end = System.currentTimeMillis();

        System.out.printf("Generate %d ids, spend %d mills", maxValue, end - start);
    }

    @Test
    void peformance() {
        final int maxValue = 10000000;

        long start = System.currentTimeMillis();

        for (int i = 0; i < maxValue; i++) {
            generator.nextId();
        }

        long end = System.currentTimeMillis();

        System.out.printf("Generate %d ids, in %d mills", maxValue, end - start);
    }
}
