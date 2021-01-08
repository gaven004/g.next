package com.g.commons.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.g.commons.enums.Status;

class EnumUtilTest {
    @Test
    void getDescMap() {
        System.out.println(EnumUtil.getDescMap(Status.class));
    }

    @Test
    void getDescOptions() {
        System.out.println(EnumUtil.getDescOptions(Status.class));
    }
}