package com.g.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhongsh
 * @version 2017/7/8
 */
@Controller
public class MainController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/login";
	}

	@RequestMapping(value = "/blank", method = RequestMethod.GET)
	public String blank() {
		return "/blank";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String _403() {
		return "/403";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String _404() {
		return "/404";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String _500() {
		return "/500";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "/error";
	}
}
