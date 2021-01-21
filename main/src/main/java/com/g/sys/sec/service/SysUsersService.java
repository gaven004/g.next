package com.g.sys.sec.service;

/**
 * Service for domain model class SysUsers.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysUsers
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysUsers;
import com.g.sys.sec.persistence.SysUsersRepository;

@Service
public class SysUsersService
        extends GenericService<SysUsersRepository, SysUsers, Long> {
}