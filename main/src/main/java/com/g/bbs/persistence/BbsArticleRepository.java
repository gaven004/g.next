package com.g.bbs.persistence;

/**
 * Repository for domain model class BbsArticle.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.persistence.BbsArticle
 */

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.g.bbs.model.BbsArticle;

public interface BbsArticleRepository extends
        PagingAndSortingRepository<BbsArticle, Long>, QuerydslPredicateExecutor<BbsArticle> {
}
