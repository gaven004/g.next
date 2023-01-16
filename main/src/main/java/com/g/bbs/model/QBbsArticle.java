package com.g.bbs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBbsArticle is a Querydsl query type for BbsArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBbsArticle extends EntityPathBase<BbsArticle> {

    private static final long serialVersionUID = 881656791L;

    public static final QBbsArticle bbsArticle = new QBbsArticle("bbsArticle");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final StringPath author = createString("author");

    public final NumberPath<Long> columnId = createNumber("columnId", Long.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> ctime = createDateTime("ctime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> mtime = createDateTime("mtime", java.time.LocalDateTime.class);

    public final NumberPath<Long> operator = createNumber("operator", Long.class);

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    public QBbsArticle(String variable) {
        super(BbsArticle.class, forVariable(variable));
    }

    public QBbsArticle(Path<? extends BbsArticle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBbsArticle(PathMetadata metadata) {
        super(BbsArticle.class, metadata);
    }

}

