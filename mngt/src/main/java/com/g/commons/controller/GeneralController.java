package com.g.commons.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    public T selectById(@PathVariable("id") Long id) {
        return service.selectById(id);
    }

    /**
     * 查询全部记录
     */
    @RequestMapping("list")
    @ResponseBody
    public List<T> selectList(T param) {
        return service.selectList(buildWrapper(param));
    }

    /**
     * 分页查询
     */
    @RequestMapping("page")
    @ResponseBody
    public Page<T> page(BindingPage<T> page, T param) {
        return service.selectPage(page, buildWrapper(param));
    }

    /**
     * 添加记录
     */
    @RequestMapping("add")
    @ResponseBody
    public RestApiResponse<?> insert(T record) {
        return RestApiResponse.create(service.insert(record));
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
    public RestApiResponse<?> deleteById(Long[] id) {
        return RestApiResponse.create(service.deleteBatchIds(Arrays.asList(id)));
    }

    protected EntityWrapper<T> buildWrapper(T param) {
        return WrapperHelper.buildWrapper(param);
    }
}
