package com.g.sys.msg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysMessage is a Querydsl query type for SysMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysMessage extends EntityPathBase<SysMessage> {

    private static final long serialVersionUID = -175692799L;

    public static final QSysMessage sysMessage = new QSysMessage("sysMessage");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final DateTimePath<java.time.LocalDateTime> ctime = createDateTime("ctime", java.time.LocalDateTime.class);

    public final NumberPath<Byte> hasAttachment = createNumber("hasAttachment", Byte.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final NumberPath<Long> replyTo = createNumber("replyTo", Long.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final StringPath senderName = createString("senderName");

    public final EnumPath<MessageStatus> status = createEnum("status", MessageStatus.class);

    public final DateTimePath<java.time.LocalDateTime> stime = createDateTime("stime", java.time.LocalDateTime.class);

    public final StringPath subject = createString("subject");

    public QSysMessage(String variable) {
        super(SysMessage.class, forVariable(variable));
    }

    public QSysMessage(Path<? extends SysMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysMessage(PathMetadata metadata) {
        super(SysMessage.class, metadata);
    }

}

