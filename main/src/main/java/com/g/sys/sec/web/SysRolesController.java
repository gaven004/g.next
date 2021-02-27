package com.g.sys.sec.web;

/**
 * Controller for domain model class SysRoles.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysRoles
 */

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import com.g.commons.enums.Option;
import com.g.commons.enums.Status;
import com.g.commons.model.AntdPageRequest;
import com.g.commons.model.AntdResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.QSysRole;
import com.g.sys.sec.model.SysRole;
import com.g.sys.sec.persistence.SysRolesRepository;
import com.g.sys.sec.service.SysRolesService;

@RestController
@RequestMapping("sys/roles")
public class SysRolesController
        extends GenericController<SysRolesService, SysRolesRepository, SysRole, Long> {
    @GetMapping("/$options")
    AntdResponse<List<Option>> getOptions() {
        var qSysRoles = QSysRole.sysRoles;
        Predicate predicate = qSysRoles.status.eq(Status.VALID);
        Iterable<SysRole> categories = service.findAllEssential(predicate, Sort.by("name"));
        if (categories != null && categories.iterator().hasNext()) {
            List<Option> result = new ArrayList();
            categories.forEach(item -> {
                result.add(new Option(item.getId().toString(), item.getName()));
            });

            return AntdResponse.success(result);
        }
        return AntdResponse.success();
    }

    @Override
    @GetMapping
    public AntdResponse<?> find(NativeWebRequest webRequest, AntdPageRequest pageRequest) throws Exception {
        AntdResponse<?> result = super.find(webRequest, pageRequest);
        Hibernate.initialize(result);
        return result;
    }

    @Override
    @GetMapping("/{id}")
    public AntdResponse<SysRole> get(@PathVariable Long id) {
        AntdResponse<SysRole> result = super.get(id);
        Hibernate.initialize(result);
        return result;
    }
}