package com.g.sys.sec.web;

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
}
