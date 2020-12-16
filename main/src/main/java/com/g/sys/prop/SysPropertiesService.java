package com.g.sys.prop;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;

import com.g.commons.enums.Status;
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
    public void delete(Long operator, String category, String name) {
        delete(operator, new SysProperties(category, name));
    }

    @Transactional
    public void delete(Long operator, SysProperties entity) {
        repository.delete(entity);
        logService.logDelete(operator, entity);
    }

    public Optional<SysProperties> get(String category, String name) {
        return repository.findById(new SysPropertiesPK(category, name));
    }

    public Page<SysProperties> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    public Iterable<SysProperties> findByCategory(String category, String status) {
        if (!StringUtils.hasText(status)) {
            status = Status.VALID.name();
        }

        var qSysProperties = QSysProperties.sysProperties;
        Predicate predicate = qSysProperties.category.eq(category)
                .and(qSysProperties.status.eq(status));
        Sort.TypedSort<SysProperties> sort = Sort.sort(SysProperties.class);

        return repository.findAll(predicate, sort.by(SysProperties::getSortOrder).ascending());
    }
}
