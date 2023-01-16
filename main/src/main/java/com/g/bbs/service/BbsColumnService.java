package com.g.bbs.service;

/**
 * Service for domain model class BbsColumn.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.service.BbsColumn
 */

import java.util.*;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.g.bbs.model.BbsColumn;
import com.g.bbs.model.QBbsColumn;
import com.g.bbs.persistence.BbsColumnRepository;
import com.g.commons.exception.EntityNotFoundException;
import com.g.commons.exception.GenericAppException;
import com.g.commons.service.GenericService;
import com.querydsl.core.types.Predicate;

@Service
public class BbsColumnService
        extends GenericService<BbsColumnRepository, BbsColumn, Long> {

    @Override
    @Transactional
    public BbsColumn save(BbsColumn entity) {
        checkParent(entity);
        return setValue(super.save(entity));
    }

    @Override
    @Transactional
    public BbsColumn update(BbsColumn entity) {
        checkParent(entity);
        return setValue(super.update(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkChildren(id);
        super.delete(id);
    }

    @Override
    public BbsColumn get(Long id) {
        return setValue(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public Iterable<BbsColumn> findAll() {
        final Iterable<BbsColumn> iterable = repository.findAll();
        iterable.forEach(item -> setValue(item));
        return iterable;
    }

    @Override
    public Iterable<BbsColumn> findAll(Predicate predicate) {
        final Iterable<BbsColumn> iterable = repository.findAll(predicate);
        iterable.forEach(item -> setValue(item));
        return iterable;
    }

    @Override
    public Iterable<BbsColumn> findAll(Predicate predicate, Sort sort) {
        final Iterable<BbsColumn> iterable = repository.findAll(predicate, sort);
        iterable.forEach(item -> setValue(item));
        return iterable;
    }

    @Override
    public Page<BbsColumn> findAll(Predicate predicate, Pageable pageable) {
        final Page<BbsColumn> page = repository.findAll(predicate, pageable);
        page.forEach(item -> setValue(item));
        return page;
    }

    public Iterable<BbsColumn> findAllEnable() {
        final QBbsColumn qBbsColumn = QBbsColumn.bbsColumn;
        Predicate predicate = qBbsColumn.status.eq("ENABLE");
        Sort.TypedSort<BbsColumn> sort = Sort.sort(BbsColumn.class);
        return repository.findAll(predicate, sort.by(BbsColumn::getParentId).ascending()
                .and(sort.by(BbsColumn::getSort)));
    }

    public Iterable<BbsColumn> getTree() {
        final Iterable<BbsColumn> src = findAllEnable();
        List<BbsColumn> dest = new ArrayList<>();
        buildTree(dest, src, 0L, "");
        return dest;
    }

    private void buildTree(List<BbsColumn> dest, Iterable<BbsColumn> src, Long parent, String prefix) {
        src.forEach(column -> {
            if (parent.equals(column.getParentId())) {
                column.setName(prefix + column.getName());
                dest.add(column);
                buildTree(dest, src, column.getId(), prefix + "　　");
            }
        });
    }

    private BbsColumn setValue(BbsColumn entity) {
        entity.setParentName("-");
        if (entity.getParentId() != null && !entity.getParentId().equals(0L)) {
            final Optional<BbsColumn> parent = repository.findById(entity.getParentId());
            if (parent.isPresent()) {
                entity.setParentName(parent.get().getName());
            }
        }
        return entity;
    }

    private void checkParent(BbsColumn entity) {
        Set<Long> ancestors = new HashSet<>();
        int limit = 1;
        BbsColumn p = entity;

        if (p.getId() != null) {
            ancestors.add(p.getId());
            limit++;
        }

        while (p.getParentId() != null && !p.getParentId().equals(0L)) {
            if (!ancestors.add(p.getParentId())) {
                throw new GenericAppException("父栏目形成闭环，检验失败");
            }

            if (ancestors.size() > limit) {
                throw new GenericAppException("系统只支持2级栏目，检验失败");
            }

            p = repository.findById(p.getParentId())
                    .orElseThrow(() -> new GenericAppException("父栏目不存在，检验失败"));
        }
    }

    private void checkChildren(Long id) {
        var qBbsColumn = QBbsColumn.bbsColumn;
        Predicate predicate = qBbsColumn.parentId.eq(id);
        Iterable<BbsColumn> columns = repository.findAll(predicate);
        if (columns != null && columns.iterator().hasNext()) {
            throw new GenericAppException("栏目下有子栏目，不能删除");
        }
    }

}
