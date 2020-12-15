package com.g.commons.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractEntity is a Querydsl query type for AbstractEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAbstractEntity extends EntityPathBase<AbstractEntity<?>> {

    private static final long serialVersionUID = 1801150628L;

    public static final QAbstractEntity abstractEntity = new QAbstractEntity("abstractEntity");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractEntity(String variable) {
        super((Class) AbstractEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractEntity(Path<? extends AbstractEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractEntity(PathMetadata metadata) {
        super((Class) AbstractEntity.class, metadata);
    }

}

