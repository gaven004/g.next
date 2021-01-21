package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleMembersId is a Querydsl query type for SysRoleMembersId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSysRoleMembersId extends BeanPath<SysRoleMembersId> {

    private static final long serialVersionUID = 1614869992L;

    public static final QSysRoleMembersId sysRoleMembersId = new QSysRoleMembersId("sysRoleMembersId");

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSysRoleMembersId(String variable) {
        super(SysRoleMembersId.class, forVariable(variable));
    }

    public QSysRoleMembersId(Path<? extends SysRoleMembersId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleMembersId(PathMetadata metadata) {
        super(SysRoleMembersId.class, metadata);
    }

}

