package com.g.sys.prop;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.service.GenericService;
import com.g.commons.utils.NullAwareBeanUtilsBean;
import com.g.sys.log.SysLogService;

@Service
public class SysPropertyCategoriesService
        extends GenericService<SysPropertyCategoriesRepository, SysPropertyCategory, String> {
    private final SysLogService logService;

    @Autowired
    public SysPropertyCategoriesService(SysLogService logService) {
        this.logService = logService;
    }

    @Transactional
    public SysPropertyCategory save(Long operator, SysPropertyCategory entity) {
        entity = repository.save(entity);
        logService.logCreate(operator, entity);
        return entity;
    }

    @Transactional
    public SysPropertyCategory update(final Long operator, final SysPropertyCategory entity) {
        final Optional<SysPropertyCategory> optional = repository.findById(entity.getId());
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
    public void delete(Long operator, String id) {
        repository.findById(id)
                .ifPresentOrElse(
                        entity -> {
                            repository.delete(entity);
                            logService.logDelete(operator, entity);
                        },
                        () -> {
                            throw new EntityNotFoundException();
                        });
    }
}
