package com.g.sys.prop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysProperties is a Querydsl query type for SysProperties
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysProperties extends EntityPathBase<SysProperties> {

    private static final long serialVersionUID = 1556728426L;

    public static final QSysProperties sysProperties = new QSysProperties("sysProperties");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final StringPath category = createString("category");

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath properties = createString("properties");

    public final StringPath status = createString("status");

    public final StringPath value = createString("value");

    public QSysProperties(String variable) {
        super(SysProperties.class, forVariable(variable));
    }

    public QSysProperties(Path<? extends SysProperties> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysProperties(PathMetadata metadata) {
        super(SysProperties.class, metadata);
    }

}

