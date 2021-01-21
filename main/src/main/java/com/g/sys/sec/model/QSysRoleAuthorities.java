package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSysRoleAuthorities is a Querydsl query type for SysRoleAuthorities
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoleAuthorities extends EntityPathBase<SysRoleAuthorities> {

    private static final long serialVersionUID = 728036341L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSysRoleAuthorities sysRoleAuthorities = new QSysRoleAuthorities("sysRoleAuthorities");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final QSysRoleAuthoritiesId id;

    public QSysRoleAuthorities(String variable) {
        this(SysRoleAuthorities.class, forVariable(variable), INITS);
    }

    public QSysRoleAuthorities(Path<? extends SysRoleAuthorities> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSysRoleAuthorities(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSysRoleAuthorities(PathMetadata metadata, PathInits inits) {
        this(SysRoleAuthorities.class, metadata, inits);
    }

    public QSysRoleAuthorities(Class<? extends SysRoleAuthorities> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSysRoleAuthoritiesId(forProperty("id")) : null;
    }

}

