package com.g.sys.controller;

import java.util.EnumSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.g.commons.controller.GeneralController;
import com.g.commons.enums.Status;
import com.g.sys.sec.mapper.SysUsersMapper;
import com.g.sys.sec.model.Role;
import com.g.sys.sec.model.SysUsers;
import com.g.sys.sec.service.SysUsersService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gaven
 * @since 1.0
 * @version 2017-11-27
 */
@Controller
@RequestMapping("/sys/usr")
public class SysUsersController extends GeneralController<SysUsersService, SysUsersMapper, SysUsers> {
    public SysUsersController() {
        super("/sys/usr");
    }

    @Override
    public void addAttribute(ModelMap modelMap) {
        EnumSet<Role> role = EnumSet.allOf(Role.class);
        modelMap.addAttribute("role", role);
        EnumSet<Status> status = EnumSet.allOf(Status.class);
        modelMap.addAttribute("status", status);
    }
}
