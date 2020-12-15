package com.g.sys.log;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SysLogRepository extends
        PagingAndSortingRepository<SysLog, Long>, QuerydslPredicateExecutor<SysLog> {
}
