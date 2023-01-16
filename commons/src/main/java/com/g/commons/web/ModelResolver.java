package com.g.commons.web;

import org.springframework.ui.Model;

/**
 * 用于处理返回View之前给Model赋值
 *
 * @author zhongsh
 * @version 2017/2/14
 */
public interface ModelResolver {

    /**
     * 给Model赋值
     *
     * @param model
     */
    void resolve(Model model);
}
