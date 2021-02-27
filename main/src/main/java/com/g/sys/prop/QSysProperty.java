package com.g.sys.prop;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QSysProperties is a Querydsl query type for SysProperties
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysProperty extends EntityPathBase<SysProperty> {

    private static final long serialVersionUID = 1556728426L;

    public static final QSysProperty sysProperties = new QSysProperty("sysProperty");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final StringPath category = createString("category");

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath properties = createString("properties");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final EnumPath<com.g.commons.enums.Status> status = createEnum("status", com.g.commons.enums.Status.class);

    public final StringPath value = createString("value");

    public QSysProperty(String variable) {
        super(SysProperty.class, forVariable(variable));
    }

    public QSysProperty(Path<? extends SysProperty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysProperty(PathMetadata metadata) {
        super(SysProperty.class, metadata);
    }

}

