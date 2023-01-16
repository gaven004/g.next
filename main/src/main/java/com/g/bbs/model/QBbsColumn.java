package com.g.bbs.model;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QBbsColumn is a Querydsl query type for BbsColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBbsColumn extends EntityPathBase<BbsColumn> {

    private static final long serialVersionUID = -332940203L;

    public static final QBbsColumn bbsColumn = new QBbsColumn("bbsColumn");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath icon = createString("icon");

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public final StringPath status = createString("status");

    public QBbsColumn(String variable) {
        super(BbsColumn.class, forVariable(variable));
    }

    public QBbsColumn(Path<? extends BbsColumn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBbsColumn(PathMetadata metadata) {
        super(BbsColumn.class, metadata);
    }

}

