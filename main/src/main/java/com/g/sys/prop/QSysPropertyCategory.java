package com.g.sys.prop;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QSysPropertyCategories is a Querydsl query type for SysPropertyCategories
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysPropertyCategory extends EntityPathBase<SysPropertyCategory> {

    private static final long serialVersionUID = -1046322017L;

    public static final QSysPropertyCategory sysPropertyCategories = new QSysPropertyCategory("sysPropertyCategory");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final EnumPath<com.g.commons.enums.Status> status = createEnum("status", com.g.commons.enums.Status.class);

    public QSysPropertyCategory(String variable) {
        super(SysPropertyCategory.class, forVariable(variable));
    }

    public QSysPropertyCategory(Path<? extends SysPropertyCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysPropertyCategory(PathMetadata metadata) {
        super(SysPropertyCategory.class, metadata);
    }

}

