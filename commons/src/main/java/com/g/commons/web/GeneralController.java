package com.g.commons.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import com.g.commons.model.ApiResponse;
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
    ApiResponse<?> find(NativeWebRequest webRequest, Pageable pageable) throws Exception {
        // 是否分页
        boolean isPage = webRequest.getParameter("size") != null;
        // 是否排序
        boolean isSort = webRequest.getParameter("sort") != null;

        Predicate predicate = argumentResolver.resolveArgument(webRequest, domainClass);

        if (isPage) {
            return ApiResponse.success(ApiResponse.SUCCESS_MSG, service.findAll(predicate, pageable));
        }

        if (isSort) {
            return ApiResponse.success(ApiResponse.SUCCESS_MSG, service.findAll(predicate, pageable.getSort()));
        }

        return ApiResponse.success(ApiResponse.SUCCESS_MSG, service.findAll(predicate));
    }

    @GetMapping("/{id}")
    ApiResponse<T> get(@PathVariable ID id) {
        return ApiResponse.success(ApiResponse.SUCCESS_MSG, service.get(id));
    }

    @PostMapping
    ApiResponse<T> save(@RequestBody @Valid T entity) {
        return ApiResponse.success(ApiResponse.SUCCESS_MSG, service.save(entity));
    }

    @PutMapping
    ApiResponse<T> update(@RequestBody @Valid T entity) {
        return ApiResponse.success(ApiResponse.SUCCESS_MSG, service.update(entity));
    }

    @DeleteMapping("/{id}")
    ApiResponse<T> delete(@PathVariable ID id) {
        service.delete(id);
        return ApiResponse.success();
    }
}
