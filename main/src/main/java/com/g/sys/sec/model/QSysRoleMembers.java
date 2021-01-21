package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSysRoleMembers is a Querydsl query type for SysRoleMembers
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoleMembers extends EntityPathBase<SysRoleMembers> {

    private static final long serialVersionUID = 654193645L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSysRoleMembers sysRoleMembers = new QSysRoleMembers("sysRoleMembers");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final QSysRoleMembersId id;

    public QSysRoleMembers(String variable) {
        this(SysRoleMembers.class, forVariable(variable), INITS);
    }

    public QSysRoleMembers(Path<? extends SysRoleMembers> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSysRoleMembers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSysRoleMembers(PathMetadata metadata, PathInits inits) {
        this(SysRoleMembers.class, metadata, inits);
    }

    public QSysRoleMembers(Class<? extends SysRoleMembers> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSysRoleMembersId(forProperty("id")) : null;
    }

}

