package com.g.sys.prop;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.g.commons.web.ModelResolver;


/**
 * 查询系统基础信息返回前端
 *
 * @author zhongsh
 * @version 2017/4/5
 */
@Component
public class SysPropertyModelResolver implements ModelResolver {
    private final SysPropertiesService service;

    private String category;
    private String name = "_SYS_PROPERTY";

    public SysPropertyModelResolver(SysPropertiesService service) {
        this.service = service;
    }

    @Override
    public void resolve(Model model) {
        final Iterable<SysProperty> properties = service.findByCategory(category);
        model.addAttribute(name, properties);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

}
