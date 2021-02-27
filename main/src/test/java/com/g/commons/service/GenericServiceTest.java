package com.g.commons.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.g.NextApplicationTests;
import com.g.sys.prop.SysProperty;
import com.g.sys.prop.SysPropertyPK;

class GenericServiceTest extends NextApplicationTests {
    @Autowired
    SysPropertiesTestService service;

    @Test
    void all() {
        SysProperty entity = new SysProperty();
        entity.setCategory("Test2");
        entity.setName("Hello");
        entity.setValue("World");
        entity = service.save(entity);
        System.out.println("entity = " + entity);

        entity.setValue("Gaven");
        entity = service.update(entity);
        System.out.println("entity = " + entity);

        SysPropertyPK pk = new SysPropertyPK("Test2", "Hello");
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
        SysPropertyPK pk = new SysPropertyPK("Test2", "Hello");
        service.delete(pk);
    }

    @Test
    void get() {
    }
}