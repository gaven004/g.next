package com.g.sys.sec.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping(value = "/form-login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/blank")
    public String blank() {
        return "blank";
    }

    @RequestMapping(value = "/view/**")
    public String view(HttpServletRequest request) {
        final String uri = request.getRequestURI();
        return uri.substring(6);
    }
}
