package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysUserAuthorities is a Querydsl query type for SysUserAuthorities
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysUserAuthorities extends EntityPathBase<SysUserAuthorities> {

    private static final long serialVersionUID = -1629580928L;

    public static final QSysUserAuthorities sysUserAuthorities = new QSysUserAuthorities("sysUserAuthorities");

    public final StringPath authority = createString("authority");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public QSysUserAuthorities(String variable) {
        super(SysUserAuthorities.class, forVariable(variable));
    }

    public QSysUserAuthorities(Path<? extends SysUserAuthorities> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysUserAuthorities(PathMetadata metadata) {
        super(SysUserAuthorities.class, metadata);
    }

}

