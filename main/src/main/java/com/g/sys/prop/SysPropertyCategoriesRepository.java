package com.g.sys.prop;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SysPropertyCategoriesRepository extends
        PagingAndSortingRepository<SysPropertyCategories, String>, QuerydslPredicateExecutor<SysPropertyCategories> {
}
