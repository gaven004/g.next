package com.g.sys.log;

import java.io.IOException;
import java.util.Iterator;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g.commons.utils.EnumUtil;
import com.g.commons.utils.IDGenerator;
import com.github.fge.jsonpatch.diff.JsonDiff;
import com.querydsl.core.types.Predicate;

@Service
public class SysLogService {
    private final SysLogRepository repository;
    private final EntityManagerFactory emf;
    private final ObjectMapper mapper;
    private final IDGenerator idGenerator;

    @Autowired
    public SysLogService(SysLogRepository repository, EntityManagerFactory emf, ObjectMapper mapper) {
        this.repository = repository;
        this.emf = emf;
        this.mapper = mapper;
        this.idGenerator = IDGenerator.getInstance();
    }

    public SysLog logCreate(Long uid, Object obj) {
        return log(uid, SysLog.OPERATIONS.CREATE, obj);
    }

    public SysLog logUpdate(Long uid, Object obj) {
        return log(uid, SysLog.OPERATIONS.UPDATE, obj);
    }

    public SysLog logDelete(Long uid, Object obj) {
        return log(uid, SysLog.OPERATIONS.DELETE, obj);
    }

    public SysLog log(Long uid, SysLog.OPERATIONS operation, Object obj) {
        Assert.notNull(uid, "操作员不能为空");
        Assert.notNull(operation, "操作指令不能为空");
        Assert.notNull(obj, "操作对象不能为空");

        try {
            Class<?> clz = obj.getClass();
            Object keyObj = getKeyObject(obj);

            SysLog entity = new SysLog();
            entity.setId(idGenerator.nextId());
            entity.setUid(uid);
            entity.setOperation(operation.name());
            entity.setClazz(clz.getName());
            entity.setOid(mapper.writeValueAsString(keyObj));
            if (!SysLog.OPERATIONS.DELETE.equals(operation)) {
                entity.setContent(mapper.writeValueAsString(obj));
            }

            return repository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<SysLog> getTrace(Predicate predicate) {
        Assert.notNull(predicate, "目标对象不能为空");

        try {
            Sort.TypedSort<SysLog> sort = Sort.sort(SysLog.class);
            Iterable<SysLog> logs = repository.findAll(predicate,
                    sort.by(SysLog::getCtime).ascending());

            if (logs != null && logs.iterator().hasNext()) {
                translate(logs);
            }

            return logs;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Object getKeyObject(Object entity) {
        return emf.getPersistenceUnitUtil().getIdentifier(entity);
    }

    private void translate(Iterable<SysLog> logs) throws IOException {
        JsonNode source, target, patch;

        final Iterator<SysLog> iterator = logs.iterator();

        SysLog item = iterator.next();
        item.setOperation(EnumUtil.getDescription(SysLog.OPERATIONS.class, item.getOperation()));
        source = mapper.readTree(item.getContent());

        while (iterator.hasNext()) {
            item = iterator.next();

            String operation = item.getOperation();
            item.setOperation(EnumUtil.getDescription(SysLog.OPERATIONS.class, operation));

            if (SysLog.OPERATIONS.DELETE.name().equals(operation)) {
                break;
            }

            target = mapper.readTree(item.getContent());
            patch = JsonDiff.asJson(source, target);

/*
            StringBuffer buff = new StringBuffer();
            patch.forEach(diff -> {
                String op = diff.get("op").asText();
                String path = diff.get("path").asText();
                String value = diff.get("value").asText();

                if ("add".equals(op)) {
                    buff.append(String.format("新增：[%s=%s]\n", path, value));
                } else if ("remove".equals(op)) {
                    buff.append(String.format("删除：[%s]\n", path));
                } else if ("replace".equals(op)) {
                    buff.append(String.format("修改：[%s=>%s]\n", path, value));
                } else {
                    buff.append(String.format("%s：[%s=%s]\n", op, path, value));
                }
            });

            item.setDiff(buff.toString());
*/

            item.setDiff(patch.toString());

            source = target;
        }
    }
}
