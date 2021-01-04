package com.g.commons.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import com.g.commons.model.AntdPageRequest;
import com.g.commons.model.AntdResponse;
import com.g.commons.service.GeneralService;
import com.g.commons.utils.GenericsUtils;

public abstract class GeneralController<S extends GeneralService<R, T, ID>,
        R extends PagingAndSortingRepository<T, ID> & QuerydslPredicateExecutor<T>, T, ID> {
    @Autowired
    protected QuerydslPredicateArgumentCustomResolver argumentResolver;

    @Autowired
    protected S service;

    private final Class<T> domainClass;

    protected GeneralController() {
        this.domainClass = GenericsUtils.getSuperClassGenricType(getClass(), 2);
    }

    @GetMapping
    AntdResponse<?> find(NativeWebRequest webRequest, AntdPageRequest pageRequest) throws Exception {
        // 是否分页
        boolean isPage = webRequest.getParameter("pageSize") != null;
        // 是否排序
        boolean isSort = webRequest.getParameter("sorter") != null;

        Pageable pageable = (pageRequest != null ? pageRequest.toPageable() : null);

        Predicate predicate = argumentResolver.resolveArgument(webRequest, domainClass);

        if (isPage) {
            return AntdResponse.success(service.findAll(predicate, pageable));
        }

        if (isSort) {
            return AntdResponse.success(service.findAll(predicate, pageable.getSort()));
        }

        return AntdResponse.success(service.findAll(predicate));
    }

    @GetMapping("/{id}")
    AntdResponse<T> get(@PathVariable ID id) {
        return AntdResponse.success(service.get(id));
    }

    @PostMapping
    AntdResponse<T> save(@RequestBody @Valid T entity) {
        return AntdResponse.success(service.save(entity));
    }

    @PutMapping
    AntdResponse<T> update(@RequestBody @Valid T entity) {
        return AntdResponse.success(service.update(entity));
    }

    @DeleteMapping("/{id}")
    AntdResponse<T> delete(@PathVariable ID id) {
        service.delete(id);
        return AntdResponse.success();
    }
}
