package com.g.sys.sec.web;

/**
 * Controller for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysUsers
 */

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.exception.IllegalArgumentException;
import com.g.commons.model.ApiResponse;
import com.g.commons.web.ControllerHelper;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.ChangePasswordRequest;
import com.g.sys.sec.model.Email;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.persistence.SysUsersRepository;
import com.g.sys.sec.service.SysUsersService;

@Controller
@RequestMapping("sys/users")
public class SysUsersController
        extends GenericController<SysUsersService, SysUsersRepository, SysUser, Long> {
    @Override
    @GetMapping
    @ResponseBody
    public ApiResponse<?> find(NativeWebRequest webRequest, @PageableDefault Pageable pageable) throws Exception {
        ApiResponse<?> result = super.find(webRequest, pageable);
        Hibernate.initialize(result);
        return result;
    }

    @Override
    @GetMapping("/{id}")
    @ResponseBody
    public ApiResponse<SysUser> get(@PathVariable Long id) {
        ApiResponse<SysUser> result = super.get(id);
        Hibernate.initialize(result);
        return result;
    }

    @Override
    @PostMapping
    @ResponseBody
    public ApiResponse<SysUser> save(@RequestBody @Valid SysUser entity) {
        return ApiResponse.success(service.save(entity));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseBody
    public ApiResponse<SysUser> update(@RequestBody @Valid SysUser entity) {
        return ApiResponse.success(service.update(entity));
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseBody
    public ApiResponse<SysUser> patch(@RequestBody @Valid SysUser entity) {
        return ApiResponse.success(service.update(entity));
    }

    @Override
    @DeleteMapping
    @ResponseBody
    public ApiResponse<?> delete(@RequestParam(value = "ids[]") Long[] ids) {
        for (Long id : ids) {
            service.delete(id);
        }
        return ApiResponse.success();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseBody
    public ApiResponse<SysUser> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success();
    }

    @PutMapping("/current-user")
    @ResponseBody
    public ApiResponse<SysUser> updateCurrentUser(@RequestBody @Valid SysUser entity) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        if (!authUser.getId().equals(entity.getId())) {
            throw new IllegalArgumentException("只允许更新当前登录用户");
        }

        SysUser srcUser = service.get(authUser.getId());
        Hibernate.initialize(srcUser);
        entity.setRoles(srcUser.getRoles());
        return ApiResponse.success(service.update(entity));
    }

    @GetMapping("/current-user")
    @ResponseBody
    public ApiResponse<SysUser> getCurrent() {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        return get(authUser.getId());
    }

    @PutMapping("change-password")
    @ResponseBody
    public ApiResponse<?> changePassword(@Validated @RequestBody ChangePasswordRequest request) {
        SecurityUser authUser = WebSecurityHelper.getAuthUser();
        service.changePassword(authUser.getId(), request.getOldPwd(), request.getNewPwd());
        return ApiResponse.success();
    }

    @PutMapping("reset-password")
    @ResponseBody
    public ApiResponse<?> resetPassword(@Validated @RequestBody Email email) {
        service.resetPassword(email.getEmail());
        return ApiResponse.success();
    }

    @PostMapping("reset-pwd")
    public String resetPwd(Model model, String email) {
        try {
            service.resetPassword(email);
            ControllerHelper.setSuccessMsg(model, "密码已重置，并发送到注册的邮箱，请前往查收！");
        } catch (Exception e) {
            ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
        }

        return "login";
    }

    /**
     * 个人信息，展示、保存
     *
     * @param model
     * @return
     */
    @GetMapping("profile")
    public String profile(Model model) {
        SysUser user = null;

        SecurityUser su = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (su != null) {
            try {
                user = service.get(su.getId());
            } catch (Exception e) {
                ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
            }
        }

        model.addAttribute("User", user);

        return "/sys/sec/profile";
    }

    @RequestMapping(value = "/save-profile", method = RequestMethod.POST)
    public String saveProfile(@Valid @ModelAttribute("User") SysUser su, BindingResult result, Model model) {

        if (result.hasErrors()) {
            ControllerHelper.setErrorMsg(model, "请检查您的输入是否有误！");
        } else {
            SecurityUser cu = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!cu.getId().equals(su.getId())) {
                ControllerHelper.setErrorMsg(model, "请检查您的操作是否有误，系统检测到您的登录信息不正常，或者可以退出系统后再登录重试！");
            } else {
                try {
                    service.updateProfile(su);
                    ControllerHelper.setSuccessMsg(model, "成功保存记录！");
                } catch (IllegalArgumentException iae) {
                    if ("重复的用户账号".equals(iae.getMessage())) {
                        FieldError error = new FieldError("User", "account", su.getAccount(), false, null, null,
                                iae.getMessage());
                        result.addError(error);
                    } else if ("重复的用户邮箱".equals(iae.getMessage())) {
                        FieldError error = new FieldError("User", "email", su.getEmail(), false, null, null,
                                iae.getMessage());
                        result.addError(error);
                    }
                    ControllerHelper.setErrorMsg(model, "请检查您的输入是否有误！");
                } catch (Exception e) {
                    ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
                }
            }

            model.addAttribute("User", su);
        }

        return "/sys/sec/profile";
    }

    /**
     * 修改密码
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/change-pwd", method = RequestMethod.GET)
    public String changePwd(Model model) {
        return "/sys/sec/user_chg_pwd";
    }

    @RequestMapping(value = "/save-pwd", method = RequestMethod.POST)
    public String savePwd(String oldpwd, String newpwd, Model model) {
        try {
            SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            service.changePassword(user.getId(), oldpwd, newpwd);
            ControllerHelper.setSuccessMsg(model, "成功修改密码！");
        } catch (EntityNotFoundException e) {
            ControllerHelper.setErrorMsg(model, "系统无法找到对应的用户，请检查您的输入是否正确！");
        } catch (IllegalArgumentException e) {
            ControllerHelper.setErrorMsg(model, "原密码错误！");
        } catch (Exception e) {
            ControllerHelper.setErrorMsg(model, "系统操作异常！", e);
        }

        return "/sys/sec/user_chg_pwd";
    }
}
