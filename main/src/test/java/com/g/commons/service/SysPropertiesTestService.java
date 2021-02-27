package com.g.commons.service;

import org.springframework.stereotype.Service;

import com.g.sys.prop.SysProperty;
import com.g.sys.prop.SysPropertyPK;
import com.g.sys.prop.SysPropertiesRepository;

@Service
public class SysPropertiesTestService
        extends GenericService<SysPropertiesRepository, SysProperty, SysPropertyPK> {
}
