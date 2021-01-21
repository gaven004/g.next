package com.g.sys.sec.web;

/**
 * Controller for domain model class SysPersistentLogins.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysPersistentLogins
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysPersistentLogins;
import com.g.sys.sec.persistence.SysPersistentLoginsRepository;
import com.g.sys.sec.service.SysPersistentLoginsService;

@RestController
@RequestMapping("sys/SysPersistentLogins")
public class SysPersistentLoginsController
        extends GenericController<SysPersistentLoginsService, SysPersistentLoginsRepository, SysPersistentLogins, String> {
}