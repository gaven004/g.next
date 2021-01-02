package com.g.sys.prop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.querydsl.core.types.Predicate;

import com.g.NextApplicationTests;

class SysPropertiesServiceTest extends NextApplicationTests {
    @Autowired
    SysPropertiesService service;

    @Test
    void findAll() {
        var qSysProperties = QSysProperties.sysProperties;
        Predicate predicate = qSysProperties.category.containsIgnoreCase("test");
        Page<SysProperties> page = service.findAll(predicate, PageRequest.of(0, 10));
        System.out.println("page = " + page);
    }

    @Test
    void save() {
        assertNotNull(service);

        SysProperties entity = new SysProperties();
        entity.setCategory("Test");
        entity.setName("Hi");
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
        entity.setValue("World2");

        entity = service.update(0L, entity);
        assertNotNull(entity);

        System.out.println("entity = " + entity);
    }

    @Test
    void findByCategory() {
        assertNotNull(service);

        final Iterable<SysProperties> result = service.findByCategory("Test", null);
        assertNotNull(result);

        result.forEach(item->{
            System.out.printf("Item [Category: %s, Name: %s, Value: %s]\n", item.getCategory(), item.getName(), item.getValue());
        });
    }
}
