package com.g.commons.controller;

import java.util.EnumMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.enums.Status;
import com.g.commons.utils.EnumUtil;

@RestController
@RequestMapping(value = "/enums")
public class EnumController {
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public EnumMap<Status, String> status() {
        return EnumUtil.getDescMap(Status.class);
    }
}
