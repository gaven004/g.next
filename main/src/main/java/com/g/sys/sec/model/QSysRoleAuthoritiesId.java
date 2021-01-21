package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleAuthoritiesId is a Querydsl query type for SysRoleAuthoritiesId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSysRoleAuthoritiesId extends BeanPath<SysRoleAuthoritiesId> {

    private static final long serialVersionUID = -436743184L;

    public static final QSysRoleAuthoritiesId sysRoleAuthoritiesId = new QSysRoleAuthoritiesId("sysRoleAuthoritiesId");

    public final StringPath authority = createString("authority");

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QSysRoleAuthoritiesId(String variable) {
        super(SysRoleAuthoritiesId.class, forVariable(variable));
    }

    public QSysRoleAuthoritiesId(Path<? extends SysRoleAuthoritiesId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleAuthoritiesId(PathMetadata metadata) {
        super(SysRoleAuthoritiesId.class, metadata);
    }

}

