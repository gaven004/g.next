package com.g.sys.prop;

import com.g.commons.enums.Option;
import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.model.ApiResponse;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
            List<Option> result = new ArrayList();
            properties.forEach(item -> {
                result.add(new Option(item.getName(), item.getValue()));
            });

            return ApiResponse.success(result);
        }
        return ApiResponse.success();
    }

    @GetMapping("/{category}/{name}")
    ApiResponse<SysProperty> get(@PathVariable String category, @PathVariable String name) {
        return service.get(category, name)
                .map(entity -> ApiResponse.success(entity))
                .orElseThrow(() -> new EntityNotFoundException());
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
    ApiResponse<?> delete(@RequestBody @Valid SysPropertyPK[] pks) {
        for (SysPropertyPK pk : pks) {
            service.delete(0L, pk);
        }
        return ApiResponse.success();
    }

    @DeleteMapping("/{category}/{name}")
    ApiResponse<?> delete(@PathVariable String category, @PathVariable String name) {
        service.delete(0L, new SysPropertyPK(category, name));
        return ApiResponse.success();
    }
}
