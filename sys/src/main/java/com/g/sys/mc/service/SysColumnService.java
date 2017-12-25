package com.g.sys.mc.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.g.sys.mc.exception.InvalidParentException;
import com.g.sys.mc.mapper.SysColumnMapper;
import com.g.sys.mc.model.SysColumn;

/**
 * <p>
 * 系统文章栏目表 服务实现类
 * </p>
 *
 * @author Gaven
 * @since 2017-12-19
 */
@Service
public class SysColumnService extends ServiceImpl<SysColumnMapper, SysColumn> {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(SysColumn entity) {
        checkParent(entity);
        return super.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(SysColumn entity) {
        checkParent(entity);
        return super.updateById(entity);
    }

    private void checkParent(SysColumn entity) throws InvalidParentException {
        String parentId = entity.getParentId();
        if (parentId == null || !StringUtils.hasText(parentId)) {
            entity.setParentId("");
            return;
        }

        if (parentId.equals(entity.getId())) {
            throw new InvalidParentException();
        }

        Set<String> family = new HashSet<String>();
        family.add(parentId);

        SysColumn parent = selectById(parentId);
        while (parent != null) {
            parentId = parent.getParentId();
            if (parentId == null) {
                return;
            }

            if (!family.add(parentId)) {
                throw new InvalidParentException();
            }

            parent = selectById(parentId);
            if (parent == null) {
                throw new InvalidParentException();
            }
        }
    }
}
