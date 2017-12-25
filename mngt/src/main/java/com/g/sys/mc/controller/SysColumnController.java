package com.g.sys.mc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.Condition;

import com.g.commons.controller.GeneralController;
import com.g.sys.mc.mapper.SysColumnMapper;
import com.g.sys.mc.model.SysColumn;
import com.g.sys.mc.service.SysColumnService;

/**
 * <p>
 * 系统文章栏目表 前端控制器
 * </p>
 *
 * @author Gaven
 * @since 1.0
 * @version 2017-12-19
 */
@Controller
@RequestMapping("/sys/mc_column")
public class SysColumnController extends GeneralController <SysColumnService, SysColumnMapper, SysColumn> {
    public SysColumnController() {
        super("/sys/mc_column");
    }

    @Override
    public void addAttribute(ModelMap modelMap) {
        @SuppressWarnings("unchecked")
        List<SysColumn> columns = service.selectList(Condition.EMPTY);
        modelMap.addAttribute("mc_columns", columns);
    }
}
