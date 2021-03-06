package com.g.sys.sec.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.g.commons.controller.ControllerHelper;
import com.g.commons.controller.GeneralController;
import com.g.commons.enums.Status;
import com.g.commons.model.RestApiResponse;
import com.g.sys.log.model.SysLog;
import com.g.sys.log.service.SysLogService;
import com.g.sys.sec.exception.DuplicateEmailException;
import com.g.sys.sec.exception.DuplicateUserAccountException;
import com.g.sys.sec.exception.PasswordMismatchException;
import com.g.sys.sec.exception.UserNotFoundException;
import com.g.sys.sec.mapper.SysUsersMapper;
import com.g.sys.sec.model.Role;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.service.SysUsersService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gaven
 * @version 2017-11-27
 * @since 1.0
 */
@Controller
@RequestMapping("/sys/usr")
public class SysUsersController extends GeneralController<SysUsersService, SysUsersMapper, SysUser> {
    @Autowired
    SysLogService sysLogService;

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

    /**
     * 添加记录
     */
    @RequestMapping("add")
    @ResponseBody
    @Override
    public RestApiResponse<?> insert(SysUser record) {
        return RestApiResponse.create(service.save(getSecurityUser().getUid(), record));
    }

    /**
     * 修改记录
     */
    @RequestMapping("edit")
    @ResponseBody
    @Override
    public RestApiResponse<?> updateById(SysUser record) {
        return RestApiResponse.create(service.updateById(getSecurityUser().getUid(), record));
    }

    /**
     * 删除记录
     */
    @RequestMapping("del")
    @ResponseBody
    @Override
    public RestApiResponse<?> deleteById(String[] id) {
        if (id == null || id.length == 0) {
            return RestApiResponse.success();
        }

        ArrayList<Long> idList = new ArrayList<>(id.length);
        for (String s : id) {
            idList.add(Long.parseLong(s));
        }

        return RestApiResponse.create(service.removeByIds(getSecurityUser().getUid(), idList));
    }

    /**
     * 个人信息，展示、保存
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        SysUser user = null;

        SecurityUser su = getSecurityUser();

        if (su != null) {
            try {
                user = service.getById(su.getUid());
            } catch (Exception e) {
                ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
            }
        }

        model.addAttribute("User", user);

        return "/sys/profile";
    }

    @RequestMapping(value = "/save-profile", method = RequestMethod.POST)
    public String saveProfile(@Valid @ModelAttribute("User") SysUser su, BindingResult result, Model model) {
        if (result.hasErrors()) {
            ControllerHelper.setErrorMsg(model, "请检查您的输入是否有误！");
        } else {
            SecurityUser cu = getSecurityUser();

            if (!cu.getUid().equals(su.getUid())) {
                ControllerHelper.setErrorMsg(model, "请检查您的操作是否有误，系统检测到您的登录信息不正常，或者可以退出系统后再登录重试！");
            } else {
                try {
                    SysUser du = new SysUser();
                    du.setUid(su.getUid());
                    du.setUsername(su.getUsername());
                    du.setEmail(su.getEmail());
                    service.updateById(cu.getUid(), du);
                    ControllerHelper.setSuccessMsg(model, "成功保存记录！");
                } catch (DuplicateUserAccountException due) {
                    FieldError error = new FieldError("User", "account", su.getAccount(), false, null, null,
                            due.getMessage());
                    result.addError(error);
                    ControllerHelper.setErrorMsg(model, "请检查您的输入是否有误！");
                } catch (DuplicateEmailException dee) {
                    FieldError error = new FieldError("User", "email", su.getEmail(), false, null, null,
                            dee.getMessage());
                    result.addError(error);
                    ControllerHelper.setErrorMsg(model, "请检查您的输入是否有误！");
                } catch (Exception e) {
                    ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
                }
            }

            model.addAttribute("User", su);
        }

        return "/sys/profile";
    }

    /**
     * 重置密码
     *
     * @param email
     * @param model
     * @return
     */
    @RequestMapping(value = "/reset-pwd", method = RequestMethod.POST)
    public String resetPwd(@RequestParam(value = "email", required = true) String email, Model model) {
        try {
            service.resetPassword(email);
            ControllerHelper.setSuccessMsg(model, "系统已为您重新生成密码，并发送到注册邮箱，请登录您的邮箱查收。");
        } catch (UserNotFoundException e) {
            ControllerHelper.setErrorMsg(model, "系统无法找到对应的用户，请检查您输入的邮箱是否正确！");
        } catch (Exception e) {
            ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
        }

        return "/login";
    }

    /**
     * 修改密码
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/change-pwd", method = RequestMethod.GET)
    public String changePwd(Model model) {
        return "/sys/usr_chg_pwd";
    }

    @RequestMapping(value = "/save-pwd", method = RequestMethod.POST)
    public String savePwd(String oldpwd, String newpwd, Model model) {
        try {
            SecurityUser user = getSecurityUser();
            service.changePassword(user.getUid(), user.getUid(), oldpwd, newpwd);
            ControllerHelper.setSuccessMsg(model, "成功修改密码！");
        } catch (UserNotFoundException e) {
            ControllerHelper.setErrorMsg(model, "系统无法找到对应的用户，请检查您的输入是否正确！");
        } catch (PasswordMismatchException e) {
            ControllerHelper.setErrorMsg(model, "原密码错误！");
        } catch (Exception e) {
            ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
        }

        return "/sys/usr_chg_pwd";
    }

    /**
     * 个人信息，展示、保存
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/trace", method = RequestMethod.GET)
    public String trace(Model model, Long id) {
        model.addAttribute("traceUrl", String.format("rest/sys/usr/trace/%s", id));
        return "/sys/log";
    }

    @RequestMapping("/trace/{id}")
    @ResponseBody
    public List<SysLog> listTrace(@PathVariable("id") Long id) {
        final SysUser user = service.getById(id);
        return sysLogService.getTrace(user);
    }

    private SecurityUser getSecurityUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
