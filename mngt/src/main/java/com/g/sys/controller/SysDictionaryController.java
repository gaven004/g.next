package com.g.sys.controller;

import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.Condition;
import com.g.commons.controller.GeneralController;
import com.g.commons.enums.Status;
import com.g.sys.dict.mapper.SysDictionaryMapper;
import com.g.sys.dict.model.SysDictCategory;
import com.g.sys.dict.model.SysDictionary;
import com.g.sys.dict.service.SysDictCategoryService;
import com.g.sys.dict.service.SysDictionaryService;

/**
 * <p>
 * 系统字典表 前端控制器
 * </p>
 *
 * @author Alfred Huang
 * @since 1.0
 * @version 2017-07-18
 */
@Controller
@RequestMapping("/sys/dict")
public class SysDictionaryController
        extends GeneralController<SysDictionaryService, SysDictionaryMapper, SysDictionary> {

    @Autowired
    private SysDictCategoryService sysDictCategoryService;

    public SysDictionaryController() {
        super("/sys/dict");
    }

    /**
     * 查询全部记录
     */
    @RequestMapping("{category}/index")
    public String load(@PathVariable String category, ModelMap modelMap) {
        addAttribute(modelMap);
        return "/sys/dict_" + category;
    }

    @Override
    public void addAttribute(ModelMap modelMap) {
        @SuppressWarnings("unchecked")
        List<SysDictCategory> dictCategorys = sysDictCategoryService.selectList(Condition.EMPTY);
        modelMap.addAttribute("dict_category", dictCategorys);
        EnumSet<Status> status = EnumSet.allOf(Status.class);
        modelMap.addAttribute("status", status);
    }
}
