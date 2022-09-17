package com.g.sys.prop;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.g.commons.enums.Status;
import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.utils.NullAwareBeanUtilsBean;
import com.g.sys.log.SysLogService;
import com.querydsl.core.types.Predicate;

@Service
public class SysPropertiesService {
    private final SysPropertiesRepository repository;
    private final SysLogService logService;

    @Autowired
    public SysPropertiesService(SysPropertiesRepository repository, SysLogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Transactional
    public SysProperty save(Long operator, SysProperty entity) {
        entity = repository.save(entity);
        logService.logCreate(operator, entity);
        return entity;
    }

    @Transactional
    public SysProperty update(final Long operator, final SysProperty entity) {
        final Optional<SysProperty> optional = get(entity.getId());
        return optional
                .map(source -> {
                    try {
                        NullAwareBeanUtilsBean.getInstance().copyProperties(source, entity);
                    } catch (IllegalAccessException e) {
                        // Ignore
                    } catch (InvocationTargetException e) {
                        // Ignore
                    }
                    source = repository.save(source);
                    logService.logUpdate(operator, source);
                    return source;
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional
    public void delete(Long operator, Long pk) {
        repository.deleteById(pk);
        logService.logDelete(operator, new SysProperty(pk));
    }

    public Optional<SysProperty> get(Long pk) {
        return repository.findById(pk);
    }

    public Page<SysProperty> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    public Iterable<SysProperty> findByCategory(String category) {
        return findByCategory(category, null);
    }

    public Iterable<SysProperty> findByCategory(String category, Status status) {
        if (null == status) {
            status = Status.VALID;
        }

        var qSysProperties = QSysProperty.sysProperties;
        Predicate predicate = qSysProperties.category.eq(category)
                .and(qSysProperties.status.eq(status));
        Sort.TypedSort<SysProperty> sort = Sort.sort(SysProperty.class);

        return repository.findAll(predicate, sort.by(SysProperty::getSortOrder).ascending());
    }
}
