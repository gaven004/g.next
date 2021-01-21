package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysUserAuthoritiesId is a Querydsl query type for SysUserAuthoritiesId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSysUserAuthoritiesId extends BeanPath<SysUserAuthoritiesId> {

    private static final long serialVersionUID = 1635793595L;

    public static final QSysUserAuthoritiesId sysUserAuthoritiesId = new QSysUserAuthoritiesId("sysUserAuthoritiesId");

    public final StringPath authority = createString("authority");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public QSysUserAuthoritiesId(String variable) {
        super(SysUserAuthoritiesId.class, forVariable(variable));
    }

    public QSysUserAuthoritiesId(Path<? extends SysUserAuthoritiesId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysUserAuthoritiesId(PathMetadata metadata) {
        super(SysUserAuthoritiesId.class, metadata);
    }

}

