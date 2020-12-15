package com.g.sys.prop;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.g.NextApplicationTests;

class SysPropertiesServiceTest extends NextApplicationTests {
    @Autowired
    SysPropertiesService service;

    @Test
    void save() {
        assertNotNull(service);

        SysProperties entity = new SysProperties();
        entity.setCategory("Test");
        entity.setName("Hello");
        entity.setValue("World");

        entity = service.save(0L, entity);
        assertNotNull(entity);

        System.out.println("entity = " + entity);
    }

    @Test
    void update() {
        assertNotNull(service);

        SysProperties entity = new SysProperties();
        entity.setCategory("Test");
        entity.setName("Hello");
        entity.setValue("Gaven");

        entity = service.update(0L, entity);
        assertNotNull(entity);

        System.out.println("entity = " + entity);
    }
}
