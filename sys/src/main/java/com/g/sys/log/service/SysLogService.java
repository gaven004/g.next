package com.g.sys.log.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;

import com.g.commons.utils.EnumUtil;
import com.g.sys.log.mapper.SysLogMapper;
import com.g.sys.log.model.SysLog;
import com.g.sys.sec.model.SysUser;
import com.g.sys.sec.service.SysUsersService;

/**
 * <p>
 * 对象变更日志 服务实现类
 * </p>
 *
 * @author Gaven
 * @since 2019-01-14
 */
@Service
public class SysLogService extends ServiceImpl<SysLogMapper, SysLog> {
    @Autowired
    private SysUsersService usersService;

    private EnumSet<DiffFlags> diffFlags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();

    public int logCreate(String uid, Object obj) {
        return log(uid, SysLog.OPERATIONS.CREATE, obj);
    }

    public int logUpdate(String uid, Object obj) {
        return log(uid, SysLog.OPERATIONS.UPDATE, obj);
    }

    public int logDelete(String uid, Object obj) {
        return log(uid, SysLog.OPERATIONS.DELETE, obj);
    }

    public int log(String uid, SysLog.OPERATIONS operation, Object obj) {
        Assert.hasText(uid, "操作员不能为空");
        Assert.notNull(operation, "操作指令不能为空");
        Assert.notNull(obj, "操作对象不能为空");

        Class<?> clz = obj.getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clz);
        Assert.notNull(tableInfo, "传入的对象不是数据中的实体");
        Assert.hasText(tableInfo.getKeyProperty(), "传入的对象没有主键");

        try {
            JSONObject keyObj = getKeyObject(obj, clz, tableInfo);

            SysLog entity = new SysLog();
            entity.setUid(uid);
            entity.setOperation(operation.name());
            entity.setClazz(clz.getName());
            entity.setOid(JSON.toJSONString(keyObj));
            if (!SysLog.OPERATIONS.DELETE.equals(operation)) {
                entity.setContent(JSON.toJSONString(obj));
            }

            return baseMapper.insert(entity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<SysLog> getTrace(Object obj) {
        Assert.notNull(obj, "目标对象不能为空");

        Class<?> clz = obj.getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clz);
        Assert.notNull(tableInfo, "传入的对象不是数据中的实体");
        Assert.hasText(tableInfo.getKeyProperty(), "传入的对象没有主键");

        try {
            JSONObject keyObj = getKeyObject(obj, clz, tableInfo);

            QueryWrapper<SysLog> wrapper = new QueryWrapper();
            wrapper.eq(SysLog.CLAZZ, clz.getName());
            wrapper.eq(SysLog.OID, JSON.toJSONString(keyObj));
            wrapper.orderByAsc(SysLog.ID);

            List<SysLog> list = baseMapper.selectList(wrapper);
            if (list != null && !list.isEmpty()) {
                translate(list);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void translate(List<SysLog> list) throws IOException {
        JsonNode source, target, patch;

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SysLog item = list.get(0);
        SysUser user = usersService.getById(item.getUid());
        item.setOperator(user == null ? item.getUid() : user.getUsername());
        item.setOperation(EnumUtil.getDescription(SysLog.OPERATIONS.class, item.getOperation()));
        source = mapper.readTree(item.getContent());

        for (int i = 1; i < list.size(); i++) {
            item = list.get(i);

            user = usersService.getById(item.getUid());
            item.setOperator(user == null ? item.getUid() : user.getUsername());

            String operation = item.getOperation();
            item.setOperation(EnumUtil.getDescription(SysLog.OPERATIONS.class, operation));

            if (SysLog.OPERATIONS.DELETE.name().equals(operation)) {
                break;
            }

            target = mapper.readTree(item.getContent());
            patch = JsonDiff.asJson(source, target, diffFlags);

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

    private JSONObject getKeyObject(Object obj, Class<?> clz, TableInfo tableInfo) {
        boolean done = false;
        JSONObject keyObj = new JSONObject();

        // Mybatis plus中，tableInfo的KeyProperty暂不支持复合主键，所以先用标注获取主键
        List<Field> list = TableInfoHelper.getAllFields(clz);
        for (Field field : list) {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                Object value = ReflectionKit.getMethodValue(clz, obj, field.getName());
                keyObj.put(field.getName(), value);
                done = true;
            }
        }

        if (!done) {
            // 没有标注，使用Mybatis plus默认主键，即id字段
            for (String prop : tableInfo.getKeyProperty().split(StringPool.COMMA)) {
                Object value = ReflectionKit.getMethodValue(clz, obj, prop);
                keyObj.put(prop, value);
            }
        }

        return keyObj;
    }
}
