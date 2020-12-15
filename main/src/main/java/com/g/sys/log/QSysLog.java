package com.g.sys.log;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysLog is a Querydsl query type for SysLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysLog extends EntityPathBase<SysLog> {

    private static final long serialVersionUID = 2060203574L;

    public static final QSysLog sysLog = new QSysLog("sysLog");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final StringPath clazz = createString("clazz");

    public final StringPath content = createString("content");

    public final DateTimePath<java.sql.Timestamp> ctime = createDateTime("ctime", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath oid = createString("oid");

    public final StringPath operation = createString("operation");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public QSysLog(String variable) {
        super(SysLog.class, forVariable(variable));
    }

    public QSysLog(Path<? extends SysLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysLog(PathMetadata metadata) {
        super(SysLog.class, metadata);
    }

}

