package com.g.sys.prop;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.g.sys.log.SysLogService;

@Service
public class SysPropertiesService {
    private final SysPropertiesRepository repository;
    private final SysLogService logService;

    public SysPropertiesService(SysPropertiesRepository repository, SysLogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Transactional
    public SysProperties save(Long operator, SysProperties entity) {
        entity = repository.save(entity);
        logService.logCreate(operator, entity);
        return entity;
    }

    @Transactional
    public SysProperties update(Long operator, SysProperties entity) {
        entity.markNotNew();
        entity = repository.save(entity);
        logService.logUpdate(operator, entity);
        return entity;
    }

    @Transactional
    public void delete(Long operator, SysProperties entity) {
        repository.delete(entity);
        logService.logDelete(operator, entity);
    }
}
