package com.g.sys.sec.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysMenu is a Querydsl query type for SysMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysMenu extends EntityPathBase<SysMenu> {

    private static final long serialVersionUID = -1711575947L;

    public static final QSysMenu sysMenu = new QSysMenu("sysMenu");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final StringPath icon = createString("icon");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final EnumPath<com.g.commons.enums.Status> status = createEnum("status", com.g.commons.enums.Status.class);

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public final StringPath component = createString("component");

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public QSysMenu(String variable) {
        super(SysMenu.class, forVariable(variable));
    }

    public QSysMenu(Path<? extends SysMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysMenu(PathMetadata metadata) {
        super(SysMenu.class, metadata);
    }

}

