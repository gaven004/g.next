package com.g.sys.sec.service;

/**
 * Service for domain model class SysMenu.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysMenu
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysMenu;
import com.g.sys.sec.persistence.SysMenuRepository;

@Service
public class SysMenuService
        extends GenericService<SysMenuRepository, SysMenu, Long> {
}