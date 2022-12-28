package com.g.commons.utils;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.CoreLogging;
import org.hibernate.internal.CoreMessageLogger;

public class HibernateSnowflakeGenerator implements IdentifierGenerator {
    private static final CoreMessageLogger LOG = CoreLogging.messageLogger(HibernateSnowflakeGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return IDGenerator.getInstance().nextId();
    }
}
