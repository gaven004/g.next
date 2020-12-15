package com.g.sys.log;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.Predicate;

import com.g.NextApplicationTests;
import com.g.sys.prop.SysProperties;

class SysLogServiceTest extends NextApplicationTests {
    @Autowired
    SysLogService service;

    @Test
    void getTrace() {
        var qSysLog = QSysLog.sysLog;
        Predicate predicate = qSysLog.clazz.eq(SysProperties.class.getName());

        final Iterable<SysLog> trace = service.getTrace(predicate);
        trace.forEach(log -> {
            System.out.println(log);
        });
    }
}
