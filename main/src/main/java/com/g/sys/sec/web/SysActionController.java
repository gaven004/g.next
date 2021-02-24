package com.g.sys.sec.web;

/**
 * Controller for domain model class SysAction.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysAction
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import com.g.commons.enums.Option;
import com.g.commons.enums.Status;
import com.g.commons.model.AntdResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.QSysAction;
import com.g.sys.sec.model.SysAction;
import com.g.sys.sec.persistence.SysActionRepository;
import com.g.sys.sec.service.SysActionService;

@RestController
@RequestMapping("sys/action")
public class SysActionController
        extends GenericController<SysActionService, SysActionRepository, SysAction, Long> {
    @GetMapping("/$options")
    AntdResponse<List<Option>> getOptions() {
        var qSysAction = QSysAction.sysAction;
        Predicate predicate = qSysAction.status.eq(Status.VALID);
        Iterable<SysAction> actions = service.findAll(predicate, Sort.by("resource"));
        if (actions != null && actions.iterator().hasNext()) {
            List<Option> result = new ArrayList();
            actions.forEach(item -> {
                result.add(new Option(item.getId().toString(), item.getDescription()));
            });

            return AntdResponse.success(result);
        }
        return AntdResponse.success();
    }
}