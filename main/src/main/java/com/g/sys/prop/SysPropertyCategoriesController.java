package com.g.sys.prop;

import static com.g.commons.web.GenericController.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.g.commons.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import com.g.commons.enums.Option;
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
    ApiResponse<?> find(NativeWebRequest webRequest,
                        @QuerydslPredicate(root = SysPropertyCategory.class) Predicate predicate,
                        @PageableDefault Pageable pageable) {
        // 是否分页
        boolean isPage = isPage(webRequest);
        // 是否排序
        boolean isSort = isSort(webRequest);

        if (isPage) {
            return ApiResponse.success(repository.findAll(predicate, pageable));
        }

        if (isSort) {
            return ApiResponse.success(repository.findAll(predicate, pageable.getSort()));
        }

        return ApiResponse.success(repository.findAll(predicate));
    }

    @GetMapping("/$options")
    ApiResponse<List<Option>> getOptions() {
        Iterable<SysPropertyCategory> categories = repository.findAll();
        if (categories != null && categories.iterator().hasNext()) {
            List<Option> result = new ArrayList();
            categories.forEach(item -> {
                result.add(new Option(item.getId(), item.getName()));
            });

            return ApiResponse.success(result);
        }
        return ApiResponse.success();
    }

    @GetMapping("/{category}")
    ApiResponse<SysPropertyCategory> get(@PathVariable String category) {
        return repository.findById(category)
                .map(entiry -> ApiResponse.success(entiry))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping
    ApiResponse<SysPropertyCategory> save(@RequestBody @Valid SysPropertyCategory entity) {
        return ApiResponse.success(service.save(0L, entity));
    }

    @PutMapping
    ApiResponse<SysPropertyCategory> update(@RequestBody @Valid SysPropertyCategory entity) {
        return ApiResponse.success(service.update(0L, entity));
    }

    @DeleteMapping("/$batch")
    ApiResponse<?> delete(@RequestBody String[] pks) {
        for (String pk : pks) {
            service.delete(0L, pk);
        }
        return ApiResponse.success();
    }

    @DeleteMapping("/{category}")
    ApiResponse<SysPropertyCategory> delete(@PathVariable String category) {
        service.delete(0L, category);
        return ApiResponse.success();
    }
}
