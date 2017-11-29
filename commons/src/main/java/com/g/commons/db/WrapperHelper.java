package com.g.commons.db;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

public class WrapperHelper {
    /**
     * 尝试用一个Entity构建一个EntityWrapper
     * 
     * 取Entity对应的TableInfo，检查其中的非空属性，将其加入wrapper的equals条件
     * 
     * @param param
     * @return
     */
    public static <T> EntityWrapper<T> buildWrapper(T param) {
        EntityWrapper<T> wrapper = new EntityWrapper<T>();
        
        if (param == null) {
            return wrapper;
        }
        
        Class<?> clz = param.getClass();

        try {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(clz);
            if (tableInfo != null) {
                for (TableFieldInfo field : tableInfo.getFieldList()) {
                    Object value = ReflectionKit.getMethodValue(clz, param, field.getProperty());
                    if (value != null) {
                        if (!String.class.equals(field.getPropertyType()) || StringUtils.isNotEmpty((String)value)) {
                            wrapper.eq(field.getColumn(), value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // ignore
        }

        return wrapper;
    }
}
