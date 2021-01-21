package com.g.sys.sec.service;

/**
 * Service for domain model class SysPersistentLogins.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysPersistentLogins
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysPersistentLogins;
import com.g.sys.sec.persistence.SysPersistentLoginsRepository;

@Service
public class SysPersistentLoginsService
        extends GenericService<SysPersistentLoginsRepository, SysPersistentLogins, String> {
}