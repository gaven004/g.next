package com.g.commons.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.service.SysPropertiesTestService;
import com.g.sys.prop.SysProperty;
import com.g.sys.prop.SysPropertyPK;
import com.g.sys.prop.SysPropertiesRepository;

@RestController
@RequestMapping("test/sys/properties")
public class SysPropertiesTestController extends
        GenericController<SysPropertiesTestService, SysPropertiesRepository, SysProperty, SysPropertyPK> {
}
