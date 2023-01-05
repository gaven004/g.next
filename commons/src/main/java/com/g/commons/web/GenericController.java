package com.g.commons.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.g.commons.model.ApiResponse;
import com.g.commons.model.PagedModelLite;
import com.g.commons.service.GenericService;
import com.g.commons.utils.GenericsUtils;
import com.querydsl.core.types.Predicate;

public abstract class GenericController<S extends GenericService<R, T, ID>,
        R extends PagingAndSortingRepository<T, ID> & QuerydslPredicateExecutor<T>, T, ID> {
    public static final boolean oneIndexedParameters = true;

    @Autowired
    protected QuerydslPredicateArgumentCustomResolver argumentResolver;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected S service;

    private final Class<T> domainClass;
    private final Class<ID> idClass;

    public GenericController() {
        this.domainClass = GenericsUtils.getSuperClassGenericType(getClass(), 2);
        this.idClass = GenericsUtils.getSuperClassGenericType(getClass(), 3);
    }

    @GetMapping
    public ApiResponse<?> find(NativeWebRequest webRequest, @PageableDefault Pageable pageable) throws Exception {
        Predicate predicate = argumentResolver.resolveArgument(webRequest, domainClass);

        if (isPage(webRequest)) {
            return ApiResponse.success(new PagedModelLite<>((PageImpl) service.findAll(predicate, pageable),
                    oneIndexedParameters));
        }

        if (isSort(webRequest)) {
            return ApiResponse.success(service.findAll(predicate, pageable.getSort()));
        }

        return ApiResponse.success(service.findAll(predicate));
    }

    @GetMapping("/{id}")
    public ApiResponse<T> get(@PathVariable ID id) {
        return ApiResponse.success(service.get(id));
    }

    @PostMapping
    public ApiResponse<T> save(@RequestBody @Valid T entity) {
        return ApiResponse.success(service.save(entity));
    }

    @PutMapping("/{id}")
    public ApiResponse<T> update(@RequestBody @Valid T entity) {
        return ApiResponse.success(service.update(entity));
    }

    @PatchMapping("/{id}")
    public ApiResponse<T> patch(@RequestBody @Valid T entity) {
        return ApiResponse.success(service.update(entity));
    }

    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam(value = "ids[]") ID[] ids) {
        for (ID id : ids) {
            service.delete(convert(id));
        }
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<T> delete(@PathVariable ID id) {
        service.delete(convert(id));
        return ApiResponse.success();
    }

    /**
     * 由于泛型在运行时，是没有精确的类型信息，如上面delete方法，传入的实际是一个Serializable数组，
     * 并不是期待的精确主键类型
     * <p>
     * 常见的主键类型为String、Number，这里尝试对常见的主键类型做一个转换，主要是针对String转换成Number
     */
    private ID convert(Object id) {
        if (id instanceof String && !String.class.equals(idClass)) {
            if (Long.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, Long.class);
            }

            if (Integer.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, Integer.class);
            }

            if (Short.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, Short.class);
            }

            if (Byte.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, Byte.class);
            }

            if (BigInteger.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, BigInteger.class);
            }

            if (Float.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, Float.class);
            }

            if (Double.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, Double.class);
            }

            if (BigDecimal.class.equals(idClass)) {
                return (ID) NumberUtils.parseNumber((String) id, BigDecimal.class);
            }
        }

        return (ID) id;
    }

    public static boolean isSort(NativeWebRequest webRequest) {
        return webRequest.getParameter("sort") != null;
    }

    public static boolean isPage(NativeWebRequest webRequest) {
        return webRequest.getParameter("page") != null ||
                webRequest.getParameter("size") != null;
    }
}
