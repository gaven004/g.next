package com.g.sys.sec.web;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.g.bbs.model.BbsColumn;
import com.g.bbs.service.BbsColumnService;
import com.g.commons.enums.Status;
import com.g.commons.web.ModelResolver;
import com.g.sys.sec.model.QSysUser;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.service.SysUsersService;
import com.querydsl.core.types.Predicate;

@Component
public class SysUserModelResolver implements ModelResolver {
    private final SysUsersService service;

    public SysUserModelResolver(SysUsersService service) {
        this.service = service;
    }

    @Override
    public void resolve(Model model) {
        QSysUser qSysUsers = QSysUser.sysUsers;
        Predicate predicate = qSysUsers.status.eq(Status.VALID);
        final Iterable<SysUser> users = service.findAllEssential(predicate, Sort.unsorted());
        model.addAttribute("_SYS_USERS", users);
    }
}
