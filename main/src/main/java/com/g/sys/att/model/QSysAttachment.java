package com.g.sys.att.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysAttachment is a Querydsl query type for SysAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysAttachment extends EntityPathBase<SysAttachment> {

    private static final long serialVersionUID = -114491895L;

    public static final QSysAttachment sysAttachment = new QSysAttachment("sysAttachment");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath module = createString("module");

    public final StringPath path = createString("path");

    public final StringPath srcRecode = createString("srcRecode");

    public QSysAttachment(String variable) {
        super(SysAttachment.class, forVariable(variable));
    }

    public QSysAttachment(Path<? extends SysAttachment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysAttachment(PathMetadata metadata) {
        super(SysAttachment.class, metadata);
    }

}

