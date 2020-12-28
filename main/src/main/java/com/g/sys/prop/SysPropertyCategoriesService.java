package com.g.sys.prop;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.utils.NullAwareBeanUtilsBean;
import com.g.sys.log.SysLogService;

@Service
public class SysPropertyCategoriesService {
    private final SysPropertyCategoriesRepository repository;
    private final SysLogService logService;

    @Autowired
    public SysPropertyCategoriesService(SysPropertyCategoriesRepository repository, SysLogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Transactional
    public SysPropertyCategories save(Long operator, SysPropertyCategories entity) {
        entity = repository.save(entity);
        logService.logCreate(operator, entity);
        return entity;
    }

    @Transactional
    public SysPropertyCategories update(final Long operator, final SysPropertyCategories entity) {
        final Optional<SysPropertyCategories> optional = repository.findById(entity.getId());
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
