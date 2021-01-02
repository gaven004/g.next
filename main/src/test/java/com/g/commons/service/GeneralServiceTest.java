package com.g.commons.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.g.NextApplicationTests;
import com.g.sys.prop.SysProperties;
import com.g.sys.prop.SysPropertiesPK;

class GeneralServiceTest extends NextApplicationTests {
    @Autowired
    SysPropertiesTestService service;

    @Test
    void all() {
        SysProperties entity = new SysProperties();
        entity.setCategory("Test2");
        entity.setName("Hello");
        entity.setValue("World");
        entity = service.save(entity);
        System.out.println("entity = " + entity);

        entity.setValue("Gaven");
        entity = service.update(entity);
        System.out.println("entity = " + entity);

        SysPropertiesPK pk = new SysPropertiesPK("Test2", "Hello");
        entity = service.get(pk);
        System.out.println("entity = " + entity);

        service.delete(pk);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        SysPropertiesPK pk = new SysPropertiesPK("Test2", "Hello");
        service.delete(pk);
    }

    @Test
    void get() {
    }
}