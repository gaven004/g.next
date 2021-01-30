package com.g.sys.sec.web;

/**
 * Controller for domain model class SysMenu.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysMenu
 */

import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.model.AntdResponse;
import com.g.commons.web.GenericController;
import com.g.sys.sec.model.SysMenu;
import com.g.sys.sec.model.SysMenuDto;
import com.g.sys.sec.model.SysMenuDto4Select;
import com.g.sys.sec.persistence.SysMenuRepository;
import com.g.sys.sec.service.SysMenuService;

@RestController
@RequestMapping("sys/menu")
public class SysMenuController
        extends GenericController<SysMenuService, SysMenuRepository, SysMenu, Long> {
    @GetMapping("/$tree")
    AntdResponse<List<SysMenuDto4Select>> getTree() {
        SysMenuDto4Select root = new SysMenuDto4Select("root", 0L);

        Long previousParent = 0L;
        SysMenuDto4Select current = root;

        Iterable<SysMenu> iterable = service.findAll();
        if (iterable != null) {
            Iterator<SysMenu> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                SysMenu item = iterator.next();
                if (previousParent != item.getParentId()) {
                    previousParent = item.getParentId();
                    current = find(root, previousParent);
                }
                current.addChild(new SysMenuDto4Select(item.getLabel(), item.getId()));
            }
        }

        return AntdResponse.success(root.getChildren());
    }

    private SysMenuDto4Select find(SysMenuDto4Select root, Long id) {
        if (root.getValue().equals(id)) {
            return root;
        }

        if (root.getChildren() == null) {
            return null;
        }

        for (SysMenuDto4Select child : root.getChildren()) {
            SysMenuDto4Select result = find(child, id);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    @GetMapping("/$menu")
    AntdResponse<List<SysMenuDto>> getMenu() {
        SysMenuDto root = new SysMenuDto("0");

        Long previousParent = 0L;
        SysMenuDto current = root;

        Iterable<SysMenu> iterable = service.findAllValid();
        if (iterable != null) {
            Iterator<SysMenu> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                SysMenu item = iterator.next();
                if (previousParent != item.getParentId()) {
                    previousParent = item.getParentId();
                    current = find(root, previousParent);
                }
                current.addRoute(new SysMenuDto(item));
            }
        }

        return AntdResponse.success(root.getRoutes());
    }

    private SysMenuDto find(SysMenuDto root, Long id) {
        if (root.getName().equals(id.toString())) {
            return root;
        }

        if (root.getRoutes() == null) {
            return null;
        }

        for (SysMenuDto child : root.getRoutes()) {
            SysMenuDto result = find(child, id);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}