package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleAuthorities is a Querydsl query type for SysRoleAuthorities
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoleAuthorities extends EntityPathBase<SysRoleAuthorities> {

    private static final long serialVersionUID = 728036341L;

    public static final QSysRoleAuthorities sysRoleAuthorities = new QSysRoleAuthorities("sysRoleAuthorities");

    public final StringPath authority = createString("authority");

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QSysRoleAuthorities(String variable) {
        super(SysRoleAuthorities.class, forVariable(variable));
    }

    public QSysRoleAuthorities(Path<? extends SysRoleAuthorities> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleAuthorities(PathMetadata metadata) {
        super(SysRoleAuthorities.class, metadata);
    }

}

