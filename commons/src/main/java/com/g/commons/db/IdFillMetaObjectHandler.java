package com.g.commons.db;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.g.commons.utils.UUIDHexGenerator;

/**
 * 自动填充String类型的主键
 *
 * @author zhongsh
 * @version 2017/7/13
 * @since 1.0
 */
public class IdFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Field[] fields = metaObject.getOriginalObject().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (String.class.equals(field.getType())) {
                    Object fieldVal = getFieldValByName(field.getName(), metaObject); // mybatis-plus版本2.0.9+
                    // 空的String类型字段才填充
                    if (StringUtils.isBlank((String) fieldVal)) {
                        // 字段必须有注解 @TableId(value = "xxx", type = IdType.INPUT)
                        TableId tableId = field.getAnnotation(TableId.class);
                        if (tableId != null && tableId.type() == IdType.INPUT) {
                            setFieldValByName(field.getName(), UUIDHexGenerator.generate(), metaObject); // mybatis-plus版本2.0.9+
                            break;
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }
}
