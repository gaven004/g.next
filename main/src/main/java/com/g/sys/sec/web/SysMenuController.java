package com.g.sys.sec.web;

/**
 * Controller for domain model class SysMenu.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysMenu
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysMenu;
import com.g.sys.sec.persistence.SysMenuRepository;
import com.g.sys.sec.service.SysMenuService;

@RestController
@RequestMapping("sys/menu")
public class SysMenuController
        extends GenericController<SysMenuService, SysMenuRepository, SysMenu, Long> {
}