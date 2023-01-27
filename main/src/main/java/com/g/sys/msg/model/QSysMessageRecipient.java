package com.g.sys.msg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysMessageRecipient is a Querydsl query type for SysMessageRecipient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysMessageRecipient extends EntityPathBase<SysMessageRecipient> {

    private static final long serialVersionUID = 1121869592L;

    public static final QSysMessageRecipient sysMessageRecipient = new QSysMessageRecipient("sysMessageRecipient");

    public final com.g.commons.model.QAbstractEntity _super = new com.g.commons.model.QAbstractEntity(this);

    public final NumberPath<Byte> delFlag = createNumber("delFlag", Byte.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> messageId = createNumber("messageId", Long.class);

    public final NumberPath<Long> recipientId = createNumber("recipientId", Long.class);

    public final StringPath recipientName = createString("recipientName");

    public final DateTimePath<java.time.LocalDateTime> rtime = createDateTime("rtime", java.time.LocalDateTime.class);

    public final EnumPath<MessageTag> tag = createEnum("tag", MessageTag.class);

    public QSysMessageRecipient(String variable) {
        super(SysMessageRecipient.class, forVariable(variable));
    }

    public QSysMessageRecipient(Path<? extends SysMessageRecipient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysMessageRecipient(PathMetadata metadata) {
        super(SysMessageRecipient.class, metadata);
    }

}

