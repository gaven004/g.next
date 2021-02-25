package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSysRoles is a Querydsl query type for SysRoles
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoles extends EntityPathBase<SysRoles> {

    private static final long serialVersionUID = -1514333593L;

    public static final QSysRoles sysRoles = new QSysRoles("sysRoles");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final SetPath<SysAction, QSysAction> authorities = this.<SysAction, QSysAction>createSet("authorities", SysAction.class, QSysAction.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<com.g.commons.enums.Status> status = createEnum("status", com.g.commons.enums.Status.class);

    public QSysRoles(String variable) {
        super(SysRoles.class, forVariable(variable));
    }

    public QSysRoles(Path<? extends SysRoles> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoles(PathMetadata metadata) {
        super(SysRoles.class, metadata);
    }

}

