package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysUsers is a Querydsl query type for SysUsers
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysUsers extends EntityPathBase<SysUsers> {

    private static final long serialVersionUID = -1511450190L;

    public static final QSysUsers sysUsers = new QSysUsers("sysUsers");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final StringPath account = createString("account");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final EnumPath<com.g.commons.enums.Status> status = createEnum("status", com.g.commons.enums.Status.class);

    public final StringPath username = createString("username");

    public QSysUsers(String variable) {
        super(SysUsers.class, forVariable(variable));
    }

    public QSysUsers(Path<? extends SysUsers> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysUsers(PathMetadata metadata) {
        super(SysUsers.class, metadata);
    }

}

