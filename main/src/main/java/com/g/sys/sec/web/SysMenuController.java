package com.g.sys.sec.web;

/**
 * Controller for domain model class SysMenu.
 *
 * @author Hibernate Tools
 * @see com.g.sys.sec.web.SysMenu
 */

import java.util.Iterator;
import java.util.List;

import com.g.commons.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    WebSecurity webSecurity;

    @GetMapping("/$tree")
    ApiResponse<List<SysMenuDto4Select>> getTree() {
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

        return ApiResponse.success(root.getChildren());
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
    ApiResponse<List<SysMenuDto>> getMenu() {
        Authentication authentication = WebSecurityHelper.getAuthentication();

        SysMenuDto root = new SysMenuDto("0");

        Long previousParent = 0L;
        SysMenuDto current = root;

        Iterable<SysMenu> iterable = service.findAllValid();
        if (iterable != null) {
            Iterator<SysMenu> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                SysMenu item = iterator.next();

                // 权限检查
                if (StringUtils.hasText(item.getUrl())) {
                    if (!webSecurity.check(authentication, item.getUrl(), null)) {
                        continue;
                    }
                }

                if (previousParent != item.getParentId()) {
                    // 寻找当前的item，应挂在哪一个菜单节点下
                    previousParent = item.getParentId();
                    current = find(root, previousParent);
                }

                if (current != null) {
                    current.addRoute(new SysMenuDto(item));
                }
            }
        }

        filter(root);

        return ApiResponse.success(root.getRoutes());
    }

    /**
     * 据已生成的菜单树，寻找给定id的位置
     */
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

    /**
     * 过滤菜单项，若菜单项自身不具有path（只用于菜单的组织展示，不可执行），或者不具有可执行的子菜单项
     * 则认为是一个空的菜单项，删除
     */
    private void filter(SysMenuDto root) {
        Iterator<SysMenuDto> iterator = root.getRoutes().iterator();
        while (iterator.hasNext()) {
            SysMenuDto next = iterator.next();
            if (!StringUtils.hasText(next.getPath())) {
                if (empty(next)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 判断是否挂有子菜单，为空是返回true
     */
    private boolean empty(SysMenuDto menuDto) {
        if (menuDto.getRoutes() == null || menuDto.getRoutes().isEmpty()) {
            return true;
        }

        filter(menuDto);

        if (menuDto.getRoutes() == null || menuDto.getRoutes().isEmpty()) {
            return true;
        }

        return false;
    }
}