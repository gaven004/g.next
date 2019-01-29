package com.g.commons.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class IDGeneratorTest {
    @Autowired
    @Qualifier("testIDGenerator")
    private IDGenerator idGenerator;

    @Test
    public void nextId() {
        System.out.println(idGenerator.nextId());
        System.out.println(idGenerator.nextId());
        System.out.println(idGenerator.nextId());
        System.out.println(idGenerator.nextId());
    }
}