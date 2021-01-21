package com.g.sys.sec.service;

/**
 * Service for domain model class SysAction.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysAction
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.SysAction;
import com.g.sys.sec.persistence.SysActionRepository;

@Service
public class SysActionService
        extends GenericService<SysActionRepository, SysAction, Long> {
}