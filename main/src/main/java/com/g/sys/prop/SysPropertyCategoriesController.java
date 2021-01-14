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
    AntdResponse<?> find(NativeWebRequest webRequest,
                         @QuerydslPredicate(root = SysPropertyCategories.class) Predicate predicate,
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
        Iterable<SysPropertyCategories> categories = repository.findAll();
        if (categories != null && categories.iterator().hasNext()) {
            List<Option> result = new ArrayList();
            categories.forEach(item -> {
                result.add(new Option(item.getId(), item.getName()));
            });

            return AntdResponse.success(result);
        }
        return AntdResponse.success();
    }

    @GetMapping("/{category}")
    AntdResponse<SysPropertyCategories> get(@PathVariable String category) {
        return repository.findById(category)
                .map(entiry -> AntdResponse.success(entiry))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping
    AntdResponse<SysPropertyCategories> save(@RequestBody @Valid SysPropertyCategories entity) {
        return AntdResponse.success(service.save(0L, entity));
    }

    @PutMapping
    AntdResponse<SysPropertyCategories> update(@RequestBody @Valid SysPropertyCategories entity) {
        return AntdResponse.success(service.update(0L, entity));
    }

    @DeleteMapping("/$batch")
    AntdResponse<?> delete(@RequestBody String[] pks) {
        for (String pk : pks) {
            service.delete(0L, pk);
        }
        return AntdResponse.success();
    }

    @DeleteMapping("/{category}")
    AntdResponse<SysPropertyCategories> delete(@PathVariable String category) {
        service.delete(0L, category);
        return AntdResponse.success();
    }
}
