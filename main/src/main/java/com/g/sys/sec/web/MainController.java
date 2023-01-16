package com.g.sys.sec.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.g.commons.web.ModelResolver;

@Controller
public class MainController {
    @Resource
    private Map<String, List<ModelResolver>> modelResolvers;

    @RequestMapping(value = "/form-login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/blank")
    public String blank() {
        return "blank";
    }

    @RequestMapping(value = "/view/**")
    public String view(HttpServletRequest request, Model model) {
        final String uri = request.getRequestURI();
        final String key = uri.substring(6);

        // 页面数据赋值
        if (modelResolvers != null) {
            List<ModelResolver> list = this.modelResolvers.get(key);
            if (list == null) {
                list = this.modelResolvers.get("/" + key);
            }

            if (list != null) {
                for (ModelResolver resolver : list) {
                    resolver.resolve(model);
                }
            }
        }

        return key;
    }
}
