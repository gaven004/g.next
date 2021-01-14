package com.g.commons.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.types.Predicate;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.utils.NullAwareBeanUtilsBean;

public abstract class GenericService<R extends PagingAndSortingRepository<T, ID> & QuerydslPredicateExecutor<T>, T, ID> {
    @Autowired
    protected R repository;
    @Autowired
    protected EntityManagerFactory emf;

    @Transactional
    public T save(T entity) {
        entity = repository.save(entity);
        return entity;
    }

    @Transactional
    public T update(final T entity) {
        final Optional<T> optional = repository.findById((ID) getKeyObject(entity));
        return optional
                .map(source -> {
                    try {
                        NullAwareBeanUtilsBean.getInstance().copyProperties(source, entity);
                    } catch (IllegalAccessException e) {
                        // Ignore
                    } catch (InvocationTargetException e) {
                        // Ignore
                    }
                    source = repository.save(source);
                    return source;
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional
    public void delete(ID id) {
        repository.deleteById(id);
    }

    public T get(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Iterable<T> findAll(Predicate predicate) {
        return repository.findAll(predicate);
    }

    public Iterable<T> findAll(Predicate predicate, Sort sort) {
        return repository.findAll(predicate, sort);
    }

    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    private Object getKeyObject(Object entity) {
        return emf.getPersistenceUnitUtil().getIdentifier(entity);
    }
}
