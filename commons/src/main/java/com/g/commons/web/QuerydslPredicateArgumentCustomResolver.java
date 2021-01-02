package com.g.commons.web;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * 由于有注解里不支持泛型，所以参考{@link org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver}，
 * 重写Querydsl解释WebRequest参数，构建Predicate的部分。
 * <p>
 * 一般情况，可直接使用 @QuerydslPredicate(root = SysProperties.class) Predicate predicate 这样直接在方法参数中获取，
 * 参考 https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#core.web.type-safe
 * 不需要使用这里解释
 */
@Component
public class QuerydslPredicateArgumentCustomResolver {
    private final QuerydslBindingsFactory bindingsFactory;
    private final QuerydslPredicateBuilder predicateBuilder;

    @Autowired
    public QuerydslPredicateArgumentCustomResolver(@Qualifier("querydslBindingsFactory") QuerydslBindingsFactory factory,
                                                   @Qualifier("mvcConversionService") Optional<ConversionService> conversionService) {
        this.bindingsFactory = factory;
        this.predicateBuilder = new QuerydslPredicateBuilder(conversionService.orElseGet(DefaultConversionService::new),
                factory.getEntityPathResolver());
    }

    @Nullable
    public Predicate resolveArgument(NativeWebRequest webRequest, Class domainClass) throws Exception {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        for (Map.Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
            parameters.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }

        TypeInformation<?> domainType = ClassTypeInformation.from(domainClass).getRequiredActualType();

        Class customizer = QuerydslBinderCustomizer.class;

        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType, customizer);

        Predicate result = predicateBuilder.getPredicate(domainType, parameters, bindings);

        if (result == null) {
            return new BooleanBuilder();
        }

        return result;
    }
}
