package com.g.sys.sec.service;

/**
 * Service for domain model class SysMenu.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.service.SysMenu
 */

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import com.g.commons.enums.Status;
import com.g.commons.exception.GenericAppException;
import com.g.commons.service.GenericService;
import com.g.sys.sec.model.QSysMenu;
import com.g.sys.sec.model.SysMenu;
import com.g.sys.sec.persistence.SysMenuRepository;

@Service
public class SysMenuService
        extends GenericService<SysMenuRepository, SysMenu, Long> {
    public Iterable<SysMenu> findAll() {
        return repository.findAll(Sort.by("parentId", "order", "id"));
    }

    public Iterable<SysMenu> findAllValid() {
        return repository.findByStatus(Status.VALID, Sort.by("parentId", "order", "id"));
    }

    @Override
    public SysMenu save(SysMenu entity) {
        checkParent(entity);
        return super.save(entity);
    }

    @Override
    public SysMenu update(SysMenu entity) {
        checkParent(entity);
        return super.update(entity);
    }

    @Override
    public void delete(Long id) {
        checkChildren(id);
        super.delete(id);
    }

    private void checkParent(SysMenu entity) {
        Set<Long> ancestors = new HashSet<>();
        SysMenu p = entity;
        if (p.getId() != null) {
            ancestors.add(p.getId());
        }
        while (p.getParentId() != 0) {
            if (!ancestors.add(p.getParentId())) {
                throw new GenericAppException("父菜单形成闭环，检验失败");
            }
            p = repository.findById(p.getParentId())
                    .orElseThrow(() -> new GenericAppException("父菜单不存在，检验失败"));
        }
    }

    private void checkChildren(Long id) {
        var qSysMenu = QSysMenu.sysMenu;
        Predicate predicate = qSysMenu.parentId.eq(id);
        Iterable<SysMenu> menus = repository.findAll(predicate);
        if (menus != null && menus.iterator().hasNext()) {
            throw new GenericAppException("菜单下有子菜单，不能删除");
        }
    }
}