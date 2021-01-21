package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSysUserAuthorities is a Querydsl query type for SysUserAuthorities
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysUserAuthorities extends EntityPathBase<SysUserAuthorities> {

    private static final long serialVersionUID = -1629580928L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSysUserAuthorities sysUserAuthorities = new QSysUserAuthorities("sysUserAuthorities");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final QSysUserAuthoritiesId id;

    public QSysUserAuthorities(String variable) {
        this(SysUserAuthorities.class, forVariable(variable), INITS);
    }

    public QSysUserAuthorities(Path<? extends SysUserAuthorities> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSysUserAuthorities(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSysUserAuthorities(PathMetadata metadata, PathInits inits) {
        this(SysUserAuthorities.class, metadata, inits);
    }

    public QSysUserAuthorities(Class<? extends SysUserAuthorities> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSysUserAuthoritiesId(forProperty("id")) : null;
    }

}

