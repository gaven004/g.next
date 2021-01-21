package com.g.commons.utils;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.jboss.logging.Logger;

import com.g.commons.service.ApplicationContextHolder;

public class HibernateSnowflakeGenerator implements IdentifierGenerator, Configurable {
    private static final CoreMessageLogger LOG = Logger.getMessageLogger(
            CoreMessageLogger.class,
            HibernateSnowflakeGenerator.class.getName()
    );

    private static IDGenerator generator;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        synchronized (this) {
            if (generator == null) {
                /**
                 * 这里引用一个Spring Bean，并不是好的设计，只是为了简化代码
                 * HibernateSnowflakeGenerator本身不是Spring Bean，这样破坏了依赖注入的原则，
                 * 并且要求强制在初始化DataSource前，先创建ApplicationContextHolder以及IDGenerator两个组件
                 */
                generator = ApplicationContextHolder.getBean(IDGenerator.class);

                if (generator == null) {
                    throw new RuntimeException("Component IDGenerator not found");
                }
            }
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return generator.nextId();
    }
}
