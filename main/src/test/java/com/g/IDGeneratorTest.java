package com.g;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.g.commons.utils.HexIDGenerator;
import com.g.commons.utils.IDGenerator;

public class IDGeneratorTest extends NextApplicationTests {
    @Autowired
    IDGenerator idGenerator;

    @Autowired
    HexIDGenerator hexIDGenerator;

    @Test
    void nextId() {
        System.out.println("id = " + idGenerator.nextId());

        System.out.println("hex id = " + hexIDGenerator.nextId());
    }
}
