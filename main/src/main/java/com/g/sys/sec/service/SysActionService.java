package com.g.sys.sec.service;

/**
 * Service for domain model class SysAction.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysAction
 */

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
import com.g.sys.sec.model.ActionMethod;
import com.g.sys.sec.model.SysAction;
import com.g.sys.sec.persistence.SysActionRepository;

@Service
public class SysActionService
        extends GenericService<SysActionRepository, SysAction, Long> {
    public Iterable<SysAction> findByResourceAndMethod(@NotEmpty String resource, @NotEmpty String method) {
        try {
            ActionMethod actionMethod = ActionMethod.valueOf(method);
            return repository.findByResourceAndMethod(resource, actionMethod);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}