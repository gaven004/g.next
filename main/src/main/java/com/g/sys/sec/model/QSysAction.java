package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysAction is a Querydsl query type for SysAction
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysAction extends EntityPathBase<SysAction> {

    private static final long serialVersionUID = -197236788L;

    public static final QSysAction sysAction = new QSysAction("sysAction");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ActionMethod> method = createEnum("method", ActionMethod.class);

    public final StringPath resource = createString("resource");

    public final StringPath description = createString("description");

    public final EnumPath<com.g.commons.enums.Status> status = createEnum("status", com.g.commons.enums.Status.class);

    public QSysAction(String variable) {
        super(SysAction.class, forVariable(variable));
    }

    public QSysAction(Path<? extends SysAction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysAction(PathMetadata metadata) {
        super(SysAction.class, metadata);
    }

}

