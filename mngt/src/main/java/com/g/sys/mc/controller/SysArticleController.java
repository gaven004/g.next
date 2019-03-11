package com.g.sys.mc.controller;

import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.g.commons.controller.GeneralController;
import com.g.commons.model.RestApiResponse;
import com.g.sys.mc.mapper.SysArticleMapper;
import com.g.sys.mc.model.ArticleState;
import com.g.sys.mc.model.SysArticle;
import com.g.sys.mc.model.SysColumn;
import com.g.sys.mc.service.SysArticleService;
import com.g.sys.mc.service.SysColumnService;
import com.g.sys.sec.model.SecurityUser;

/**
 * <p>
 * 系统文章表 前端控制器
 * </p>
 *
 * @author Gaven
 * @version 2017-12-19
 * @since 1.0
 */
@Controller
@RequestMapping("/sys/mc_article")
public class SysArticleController extends GeneralController<SysArticleService, SysArticleMapper, SysArticle> {
    private enum Action {
        NEW, UPDATE;
    }

    @Autowired
    private SysColumnService sysColumnService;

    public SysArticleController() {
        super("/sys/mc_article");
    }

    /**
     * 展示文章
     */
    @RequestMapping("browse")
    public String view(@RequestParam(name = "id") String id, ModelMap modelMap) {
        SysArticle record = service.getById(id);
        modelMap.addAttribute("article", record);
        return "/sys/mc_article_view";
    }

    /**
     * 添加记录
     */
    @RequestMapping("add")
    @ResponseBody
    public RestApiResponse<?> insert(SysArticle record) {
        setUser(record, Action.NEW);
        return RestApiResponse.create(service.save(record));
    }

    /**
     * 修改记录
     */
    @RequestMapping("edit")
    @ResponseBody
    public RestApiResponse<?> updateById(SysArticle record) {
        setUser(record, Action.UPDATE);
        return RestApiResponse.create(service.updateById(record));
    }

    @Override
    public void addAttribute(ModelMap modelMap) {
        EnumSet<ArticleState> states = EnumSet.allOf(ArticleState.class);
        modelMap.addAttribute("states", states);
        List<SysColumn> columns = sysColumnService.list();
        modelMap.addAttribute("mc_columns", columns);
    }

    private void setUser(SysArticle record, Action action) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = authentication == null ? null : (SecurityUser) authentication.getPrincipal();
        if (action == Action.NEW) {
            record.setCreateBy(user == null ? null : user.getUid());
        }
        record.setUpdateBy(user == null ? null : user.getUid());
    }
}
