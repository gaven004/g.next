package com.g.commons.web;

import java.util.EnumMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.enums.Status;
import com.g.commons.model.AntdResponse;
import com.g.commons.utils.EnumUtil;

@RestController
@RequestMapping(value = "/enums")
public class EnumsController {
    @GetMapping(value = "/status")
    public AntdResponse<EnumMap<Status, String>> status() {
        return AntdResponse.success(EnumUtil.getDescMap(Status.class));
    }
}
