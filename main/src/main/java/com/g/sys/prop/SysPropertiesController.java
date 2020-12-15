package com.g.sys.prop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import com.querydsl.core.types.Predicate;

import com.g.commons.exception.EntityNotFoundException;

@RestController
@RequestMapping("sys/properties")
public class SysPropertiesController {
    final SysPropertiesService service;

    @Autowired
    public SysPropertiesController(SysPropertiesService service) {
        this.service = service;
    }

    @GetMapping
    Page<SysProperties> find(@QuerydslPredicate(root = SysProperties.class) Predicate predicate,
                             Pageable pageable) {
        return service.findAll(predicate, pageable);
    }

    @GetMapping("/{category}/{name}")
    SysProperties get(@PathVariable String category, @PathVariable String name) {
        return service.get(category, name)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping
    SysProperties save(@RequestBody SysProperties entity) {
        return service.save(0L, entity);
    }

    @PutMapping
    SysProperties update(@RequestBody SysProperties entity) {
        return service.update(0L, entity);
    }

    @DeleteMapping("/{category}/{name}")
    void delete(@PathVariable String category, @PathVariable String name) {
        service.delete(0L, category, name);
    }
}
