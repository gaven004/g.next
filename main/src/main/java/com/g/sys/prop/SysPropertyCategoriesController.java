package com.g.sys.prop;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import com.querydsl.core.types.Predicate;

import com.g.commons.exception.EntityNotFoundException;

@RestController
@RequestMapping("sys/property/categories")
public class SysPropertyCategoriesController {
    final SysPropertyCategoriesService service;
    final SysPropertyCategoriesRepository repository;

    @Autowired
    public SysPropertyCategoriesController(SysPropertyCategoriesService service, SysPropertyCategoriesRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    Page<SysPropertyCategories> find(@QuerydslPredicate(root = SysPropertyCategories.class) Predicate predicate,
                                     Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @GetMapping("/{category}")
    SysPropertyCategories get(@PathVariable String category) {
        return repository.findById(category)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping
    SysPropertyCategories save(@RequestBody @Valid SysPropertyCategories entity) {
        return service.save(0L, entity);
    }

    @PutMapping
    SysPropertyCategories update(@RequestBody @Valid SysPropertyCategories entity) {
        return service.update(0L, entity);
    }

    @DeleteMapping("/{category}")
    void delete(@PathVariable String category) {
        service.delete(0L, category);
    }
}
