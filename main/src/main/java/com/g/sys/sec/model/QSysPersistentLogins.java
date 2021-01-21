package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysPersistentLogins is a Querydsl query type for SysPersistentLogins
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysPersistentLogins extends EntityPathBase<SysPersistentLogins> {

    private static final long serialVersionUID = 1865634231L;

    public static final QSysPersistentLogins sysPersistentLogins = new QSysPersistentLogins("sysPersistentLogins");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final DateTimePath<java.time.LocalDateTime> lastUsed = createDateTime("lastUsed", java.time.LocalDateTime.class);

    public final StringPath series = createString("series");

    public final StringPath token = createString("token");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public QSysPersistentLogins(String variable) {
        super(SysPersistentLogins.class, forVariable(variable));
    }

    public QSysPersistentLogins(Path<? extends SysPersistentLogins> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysPersistentLogins(PathMetadata metadata) {
        super(SysPersistentLogins.class, metadata);
    }

}

