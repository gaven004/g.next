package com.g.sys.sec.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

import com.g.NextApplicationTests;
import com.g.commons.enums.Status;
import com.g.sys.sec.model.QSysUser;
import com.g.sys.sec.model.SysRole;
import com.g.sys.sec.model.SysUser;

class SysUsersServiceTest extends NextApplicationTests {
    @Autowired
    SysUsersService service;

    SysRole roleAdmin = new SysRole(140228410537410560L, "系统管理员", Status.VALID);
    SysRole roleFrontend = new SysRole(144220723496353792L, "前台操作员", Status.VALID);

    @Test
    public void testCreate() {
        SysUser user = new SysUser();
        user.setAccount("gaven");
        user.setUsername("Gaven");
        user.addRole(roleAdmin);
        user = service.save(user);
        System.out.println("user = " + user);
    }

    @Test
    public void testFind() {
        SysUser user = service.get(144249868225871872L);
        System.out.println("user = " + user);
        user.getRoles().forEach(sysRoles -> {
            System.out.println("\t" + sysRoles);
        });
    }

    @Test
    public void testUpdate() {
        SysUser user = service.get(144249868225871872L);
        user.removeRole(roleAdmin);
        user.addRole(roleFrontend);
        service.update(user);

        testFind();
    }

    @Test
    public void testRemove() {
        service.delete(144165072216784896L);
    }

    @Test
    public void testFindAll() {
        Iterable<SysUser> usersIterable = service.findAll();
        usersIterable.forEach(sysUsers -> {
            System.out.println(sysUsers);
            sysUsers.getRoles().forEach(sysRoles -> {
                System.out.println("\t" + sysRoles);
            });
        });
    }

    @Test
    public void findAllEssential() {
        var qSysUsers = QSysUser.sysUsers;
        Predicate predicate = qSysUsers.status.eq(Status.VALID);
        Iterable<SysUser> usersIterable = service.findAllEssential(predicate, Sort.unsorted());
        usersIterable.forEach(sysUsers -> {
            System.out.println(sysUsers);
            sysUsers.getRoles().forEach(sysRoles -> {
                System.out.println("\t" + sysRoles);
            });
        });
    }
}