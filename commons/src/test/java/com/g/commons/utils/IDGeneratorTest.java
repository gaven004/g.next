package com.g.commons.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IDGeneratorTest {
    IDGenerator generator = new IDGenerator();

    @Test
    void nextId() {
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.nextId());
        }
    }
}
