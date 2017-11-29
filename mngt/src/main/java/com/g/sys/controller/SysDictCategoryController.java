package com.g.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.g.commons.controller.GeneralController;
import com.g.sys.dict.mapper.SysDictCategoryMapper;
import com.g.sys.dict.model.SysDictCategory;
import com.g.sys.dict.service.SysDictCategoryService;

/**
 * <p>
 * 系统字典类型表 前端控制器
 * </p>
 *
 * @author Alfred Huang
 * @since 1.0
 * @version 2017-07-18
 */
@Controller
@RequestMapping("/sys/dict_category")
public class SysDictCategoryController
        extends GeneralController<SysDictCategoryService, SysDictCategoryMapper, SysDictCategory> {
    public SysDictCategoryController() {
        super("/sys/dict_category");
    }
}
