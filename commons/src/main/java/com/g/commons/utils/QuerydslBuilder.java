package com.g.commons.utils;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;

public abstract class QuerydslBuilder {
    public static Querydsl build(Class domainClass, EntityManager em) {
        JpaEntityInformation entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
        EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());
        return new Querydsl(em, new PathBuilder(path.getType(), path.getMetadata()));
    }
}
