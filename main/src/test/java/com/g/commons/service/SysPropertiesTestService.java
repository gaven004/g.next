package com.g.commons.service;

import org.springframework.stereotype.Service;

import com.g.sys.prop.SysProperties;
import com.g.sys.prop.SysPropertiesPK;
import com.g.sys.prop.SysPropertiesRepository;

@Service
public class SysPropertiesTestService
        extends GenericService<SysPropertiesRepository, SysProperties, SysPropertiesPK> {
}
