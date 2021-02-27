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
public class QSysPersistentLogin extends EntityPathBase<SysPersistentLogin> {

    private static final long serialVersionUID = 1865634231L;

    public static final QSysPersistentLogin sysPersistentLogins = new QSysPersistentLogin("sysPersistentLogin");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final DateTimePath<java.time.LocalDateTime> lastUsed = createDateTime("lastUsed", java.time.LocalDateTime.class);

    public final StringPath series = createString("series");

    public final StringPath token = createString("token");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public QSysPersistentLogin(String variable) {
        super(SysPersistentLogin.class, forVariable(variable));
    }

    public QSysPersistentLogin(Path<? extends SysPersistentLogin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysPersistentLogin(PathMetadata metadata) {
        super(SysPersistentLogin.class, metadata);
    }

}

