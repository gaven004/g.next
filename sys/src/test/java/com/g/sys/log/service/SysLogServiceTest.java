package com.g.sys.log.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.g.sys.log.model.SysLog;
import com.g.sys.sec.model.SysUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class SysLogServiceTest {
    @Autowired
    SysLogService service;

    public void logCreate() {
        SysUser user = new SysUser();
        user.setUid(1L);
        user.setAccount("test");
        user.setUsername("test");
        user.setEnabled("VALID");

        service.logCreate(0L, user);
    }

    public void logUpdate() {
        SysUser user = new SysUser();
        user.setUid(1L);
        user.setAccount("test");
        user.setUsername("test_user");
        user.setEnabled("INVALID");

        service.logUpdate(0L, user);
    }

    public void logDelete() {
        SysUser user = new SysUser();
        user.setUid(1L);

        service.logDelete(0L, user);
    }

    public void getTrace() {
        SysUser user = new SysUser();
        user.setUid(1L);

        List<SysLog> list = service.getTrace(user);
        System.out.println(list);
    }

    public void remove() {
        QueryWrapper<SysLog> wrapper = new QueryWrapper();
        wrapper.eq(SysLog.CLAZZ, SysUser.class.getName());
        wrapper.eq(SysLog.OID, "{\"uid\":\"test\"}");
        service.remove(wrapper);
    }

    @Test
    public void testAll() {
        remove();
        logCreate();
        logUpdate();
        logUpdate();
        logUpdate();
        logDelete();
        getTrace();
    }
}