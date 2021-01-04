package com.g.sys.prop;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import com.querydsl.core.types.Predicate;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.model.AntdResponse;

@RestController
@RequestMapping("sys/properties")
public class SysPropertiesController {
    final SysPropertiesService service;

    @Autowired
    public SysPropertiesController(SysPropertiesService service) {
        this.service = service;
    }

    @GetMapping
    AntdResponse<Page<SysProperties>> find(@QuerydslPredicate(root = SysProperties.class) Predicate predicate,
                                           Pageable pageable) {
        return AntdResponse.success(service.findAll(predicate, pageable));
    }

    @GetMapping("/{category}/{name}")
    AntdResponse<SysProperties> get(@PathVariable String category, @PathVariable String name) {
        return service.get(category, name)
                .map(entity -> AntdResponse.success(entity))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping
    AntdResponse<SysProperties> save(@RequestBody SysProperties entity) {
        return AntdResponse.success(service.save(0L, entity));
    }

    @PutMapping
    AntdResponse<SysProperties> update(@RequestBody SysProperties entity) {
        return AntdResponse.success(service.update(0L, entity));
    }

    @DeleteMapping("/$batch")
    AntdResponse<?> delete(@RequestBody @Valid SysPropertiesPK[] pks) {
        for (SysPropertiesPK pk : pks) {
            service.delete(0L, pk.getCategory(), pk.getName());
        }
        return AntdResponse.success();
    }

    @DeleteMapping("/{category}/{name}")
    AntdResponse<?> delete(@PathVariable String category, @PathVariable String name) {
        service.delete(0L, category, name);
        return AntdResponse.success();
    }
}
