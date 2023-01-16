package com.g.bbs.persistence;

/**
 * Repository for domain model class BbsColumn.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.persistence.BbsColumn
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.bbs.model.BbsColumn;

public interface BbsColumnRepository extends
        PagingAndSortingRepository<BbsColumn, Long>, QuerydslPredicateExecutor<BbsColumn> {
}
