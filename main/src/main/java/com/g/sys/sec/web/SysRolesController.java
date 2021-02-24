package com.g.sys.sec.web;

/**
 * Controller for domain model class SysRoles.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysRoles
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
import com.g.sys.sec.model.QSysRoles;
import com.g.sys.sec.model.SysRoles;
import com.g.sys.sec.persistence.SysRolesRepository;
import com.g.sys.sec.service.SysRolesService;

@RestController
@RequestMapping("sys/roles")
public class SysRolesController
        extends GenericController<SysRolesService, SysRolesRepository, SysRoles, Long> {
    @GetMapping("/$options")
    AntdResponse<List<Option>> getOptions() {
        var qSysRoles = QSysRoles.sysRoles;
        Predicate predicate = qSysRoles.status.eq(Status.VALID);
        Iterable<SysRoles> categories = service.findAllEssential(predicate, Sort.by("name"));
        if (categories != null && categories.iterator().hasNext()) {
            List<Option> result = new ArrayList();
            categories.forEach(item -> {
                result.add(new Option(item.getId().toString(), item.getName()));
            });

            return AntdResponse.success(result);
        }
        return AntdResponse.success();
    }
}