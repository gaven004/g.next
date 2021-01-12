package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleMembers is a Querydsl query type for SysRoleMembers
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoleMembers extends EntityPathBase<SysRoleMembers> {

    private static final long serialVersionUID = 654193645L;

    public static final QSysRoleMembers sysRoleMembers = new QSysRoleMembers("sysRoleMembers");

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSysRoleMembers(String variable) {
        super(SysRoleMembers.class, forVariable(variable));
    }

    public QSysRoleMembers(Path<? extends SysRoleMembers> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleMembers(PathMetadata metadata) {
        super(SysRoleMembers.class, metadata);
    }

}

