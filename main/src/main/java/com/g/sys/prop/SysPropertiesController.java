package com.g.sys.prop;

import static com.g.commons.web.GenericController.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import com.g.commons.enums.Option;
import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.model.AntdPageRequest;
import com.g.commons.model.AntdResponse;

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
    AntdResponse<?> find(NativeWebRequest webRequest,
                         @QuerydslPredicate(root = SysProperty.class) Predicate predicate,
                         AntdPageRequest pageRequest) {
        // 是否分页
        boolean isPage = isPage(webRequest);
        // 是否排序
        boolean isSort = isSort(webRequest);

        Pageable pageable = getPageable(pageRequest);

        if (isPage) {
            return AntdResponse.success(repository.findAll(predicate, pageable));
        }

        if (isSort) {
            return AntdResponse.success(repository.findAll(predicate, pageable.getSort()));
        }

        return AntdResponse.success(repository.findAll(predicate));
    }

    @GetMapping("/$options")
    AntdResponse<List<Option>> getOptions() {
        Iterable<SysProperty> properties = repository.findAll();
        if (properties != null && properties.iterator().hasNext()) {
            List<Option> result = new ArrayList();
            properties.forEach(item -> {
                result.add(new Option(item.getName(), item.getValue()));
            });

            return AntdResponse.success(result);
        }
        return AntdResponse.success();
    }

    @GetMapping("/{category}/{name}")
    AntdResponse<SysProperty> get(@PathVariable String category, @PathVariable String name) {
        return service.get(category, name)
                .map(entity -> AntdResponse.success(entity))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping
    AntdResponse<SysProperty> save(@RequestBody SysProperty entity) {
        return AntdResponse.success(service.save(0L, entity));
    }

    @PutMapping
    AntdResponse<SysProperty> update(@RequestBody SysProperty entity) {
        return AntdResponse.success(service.update(0L, entity));
    }

    @DeleteMapping("/$batch")
    AntdResponse<?> delete(@RequestBody @Valid SysPropertyPK[] pks) {
        for (SysPropertyPK pk : pks) {
            service.delete(0L, pk);
        }
        return AntdResponse.success();
    }

    @DeleteMapping("/{category}/{name}")
    AntdResponse<?> delete(@PathVariable String category, @PathVariable String name) {
        service.delete(0L, new SysPropertyPK(category, name));
        return AntdResponse.success();
    }
}
