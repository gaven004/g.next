package com.g.commons.web;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import com.g.commons.model.AntdPageRequest;
import com.g.commons.model.AntdResponse;
import com.g.commons.service.GenericService;
import com.g.commons.utils.GenericsUtils;

public abstract class GenericController<S extends GenericService<R, T, ID>,
        R extends PagingAndSortingRepository<T, ID> & QuerydslPredicateExecutor<T>, T, ID> {
    @Autowired
    protected QuerydslPredicateArgumentCustomResolver argumentResolver;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected S service;

    private final Class<T> domainClass;
    private final Class<ID> idClass;

    protected GenericController() {
        this.domainClass = GenericsUtils.getSuperClassGenericType(getClass(), 2);
        this.idClass = GenericsUtils.getSuperClassGenericType(getClass(), 3);
    }

    @GetMapping
    AntdResponse<?> find(NativeWebRequest webRequest, AntdPageRequest pageRequest) throws Exception {
        // 是否分页
        boolean isPage = isPage(webRequest);
        // 是否排序
        boolean isSort = isSort(webRequest);

        Pageable pageable = getPageable(pageRequest);

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

    @DeleteMapping("/$batch")
    AntdResponse<?> delete(@RequestBody @Valid ID[] ids) {
        for (ID id : ids) {
            service.delete(convert(id));
        }
        return AntdResponse.success();
    }

    /**
     * 由于泛型在运行时，是没有精确的类型信息，如上面delete方法，传入的实际是一个Serializable数组，
     * 并不是期待的精确主键类型
     *
     * 常见的主键类型为String、Number，这里尝试对常见的主键类型做一个转换，主要是针对String转换成
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

    @DeleteMapping("/{id}")
    AntdResponse<T> delete(@PathVariable ID id) {
        service.delete(id);
        return AntdResponse.success();
    }

    public static Pageable getPageable(AntdPageRequest pageRequest) {
        return pageRequest != null ? pageRequest.toPageable() : null;
    }

    public static boolean isSort(NativeWebRequest webRequest) {
        return webRequest.getParameter("sorter") != null;
    }

    public static boolean isPage(NativeWebRequest webRequest) {
        return webRequest.getParameter("pageSize") != null;
    }
}
