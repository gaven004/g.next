package com.g.sys.prop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.g.commons.enums.Status;
import com.g.NextApplicationTests;

class SysPropertyCategoriesServiceTest extends NextApplicationTests {
    @Autowired
    SysPropertyCategoriesService service;

    @Autowired
    SysPropertyCategoriesRepository repository;

    @Test
    void save() {
        assertNotNull(service);

        SysPropertyCategories entity = new SysPropertyCategories();
        entity.setId("FILE_TYPE");
        entity.setName("文件类型");
        service.save(0L, entity);

        repository.findById("FILE_TYPE")
                .ifPresentOrElse(
                        x ->
                                System.out.println("entity = " + x),
                        () ->
                                System.out.println("Not Present."));

    }

    @Test
    void update() {
        try {
            delete();
        } catch (Exception e) {
            // ignore
        }

        save();

        SysPropertyCategories entity = new SysPropertyCategories();
        entity.setId("FILE_TYPE");
        entity.setName("文件类型");
        entity.setStatus(Status.INVALID);
        service.update(0L, entity);

        repository.findById("FILE_TYPE")
                .ifPresentOrElse(
                        x ->
                                System.out.println("entity = " + x),
                        () ->
                                System.out.println("Not Present."));
    }

    @Test
    void delete() {
        service.delete(0L, "FILE_TYPE");
    }
}
