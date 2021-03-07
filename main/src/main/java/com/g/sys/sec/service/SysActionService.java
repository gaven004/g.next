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
import com.g.sys.sec.model.SysAction;
import com.g.sys.sec.persistence.SysActionRepository;

@Service
public class SysActionService
        extends GenericService<SysActionRepository, SysAction, Long> {
    public Iterable<SysAction> findByResource(@NotEmpty String resource) {
        return repository.findByResource(resource);
    }

    /**
     * 查询有通配符的资源，为节省缓存空间，特意与findByResource分成两个方法
     *
     * @return
     */
    public Iterable<SysAction> findByResourceWithWildcard() {
        return repository.findByResourceWithWildcard();
    }
}