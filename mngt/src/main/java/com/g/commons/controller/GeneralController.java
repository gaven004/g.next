package com.g.commons.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.g.commons.db.WrapperHelper;
import com.g.commons.model.BindingPage;
import com.g.commons.model.RestApiResponse;

public abstract class GeneralController<S extends ServiceImpl<M, T>, M extends BaseMapper<T>, T> {

    @Autowired
    protected S service;

    private String indexPage;

    protected GeneralController(String indexPage) {
        this.indexPage = indexPage;
    }

    /**
     * 查询全部记录
     */
    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        addAttribute(modelMap);
        return indexPage;
    }

    /**
     * 此方法用于覆盖，给页面添加数据
     *
     * @param modelMap
     */
    public void addAttribute(ModelMap modelMap) {
    }

    /**
     * 查询单条记录
     */
    @RequestMapping("{id}")
    @ResponseBody
    public T selectById(HttpServletRequest request, @PathVariable("id") String id) {
        return service.getById(id);
    }

    /**
     * 查询全部记录
     */
    @RequestMapping("list")
    @ResponseBody
    public List<T> selectList(T param) {
        return service.list(buildWrapper(param));
    }

    /**
     * 分页查询
     */
    @RequestMapping("page")
    @ResponseBody
    public IPage<T> page(BindingPage<T> page, T param) {
        return service.page(page, buildWrapper(param));
    }

    /**
     * 添加记录
     */
    @RequestMapping("add")
    @ResponseBody
    public RestApiResponse<?> insert(T record) {
        return RestApiResponse.create(service.save(record));
    }

    /**
     * 修改记录
     */
    @RequestMapping("edit")
    @ResponseBody
    public RestApiResponse<?> updateById(T record) {
        return RestApiResponse.create(service.updateById(record));
    }

    /**
     * 删除记录
     */
    @RequestMapping("del")
    @ResponseBody
    public RestApiResponse<?> deleteById(String[] id) {
        return RestApiResponse.create(service.removeByIds(Arrays.asList(id)));
    }

    protected QueryWrapper<T> buildWrapper(T param) {
        return WrapperHelper.buildWrapper(param);
    }
}
