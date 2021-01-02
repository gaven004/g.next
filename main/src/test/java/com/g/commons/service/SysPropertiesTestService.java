package com.g.commons.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g.sys.prop.SysProperties;
import com.g.sys.prop.SysPropertiesPK;
import com.g.sys.prop.SysPropertiesRepository;

@Service
public class SysPropertiesTestService
        extends GeneralService<SysPropertiesRepository, SysProperties, SysPropertiesPK> {
}
