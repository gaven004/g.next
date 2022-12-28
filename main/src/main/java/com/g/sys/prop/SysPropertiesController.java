package com.g.sys.prop;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.g.commons.enums.Option;
import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.model.ApiResponse;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("sys/properties")
public class SysPropertiesController {
    final SysPropertiesService service;
    final SysPropertiesRepository repository;

    @Autowired
    public SysPropertiesController(SysPropertiesService service, SysPropertiesRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    ApiResponse<?> find(NativeWebRequest webRequest,
                        @QuerydslPredicate(root = SysProperty.class) Predicate predicate,
                        @PageableDefault Pageable pageable) {
        return ApiResponse.success(repository.findAll(predicate, pageable));
    }

    @GetMapping("/$options")
    ApiResponse<List<Option>> getOptions() {
        Iterable<SysProperty> properties = repository.findAll();
        if (properties != null && properties.iterator().hasNext()) {
            List<Option> result = new ArrayList<>();
            properties.forEach(item -> {
                result.add(new Option(item.getName(), item.getValue()));
            });

            return ApiResponse.success(result);
        }
        return ApiResponse.success();
    }

    @GetMapping("/{id}")
    ApiResponse<SysProperty> get(@PathVariable Long id) {
        return service.get(id)
                .map(ApiResponse::success)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    ApiResponse<SysProperty> save(@RequestBody SysProperty entity) {
        return ApiResponse.success(service.save(0L, entity));
    }

    @PutMapping
    ApiResponse<SysProperty> update(@RequestBody SysProperty entity) {
        return ApiResponse.success(service.update(0L, entity));
    }

    @DeleteMapping("/$batch")
    ApiResponse<?> delete(@RequestBody @Valid Long[] ids) {
        for (Long id : ids) {
            service.delete(0L, id);
        }
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> delete(@PathVariable Long id) {
        service.delete(0L, id);
        return ApiResponse.success();
    }
}
