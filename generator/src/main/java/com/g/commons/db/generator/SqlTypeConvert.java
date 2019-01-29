package com.g.commons.db.generator;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * 修复MySqlTypeConvert对于enum类型解析错误的bug
 *
 * @author zhongsh
 * @version 2017/08/02
 * @see com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert
 * @since 1.0
 */
public class SqlTypeConvert implements ITypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.equals("tinyint(1)")) {
            return DbColumnType.BOOLEAN;
        } else if (t.matches("enum\\(.+")) {
            return DbColumnType.STRING;
        } else if (t.matches("set\\(.+")) {
            return DbColumnType.STRING;
        } else if (t.contains("char") || t.contains("text")) {
            return DbColumnType.STRING;
        } else if (t.contains("bigint")) {
            return DbColumnType.LONG;
        } else if (t.contains("int")) {
            return DbColumnType.INTEGER;
        } else if (t.contains("date") || t.contains("time") || t.contains("year")) {
            return DbColumnType.DATE;
        } else if (t.contains("text")) {
            return DbColumnType.STRING;
        } else if (t.contains("bit")) {
            return DbColumnType.BOOLEAN;
        } else if (t.contains("decimal")) {
            return DbColumnType.BIG_DECIMAL;
        } else if (t.contains("clob")) {
            return DbColumnType.CLOB;
        } else if (t.contains("blob")) {
            return DbColumnType.BLOB;
        } else if (t.contains("binary")) {
            return DbColumnType.BYTE_ARRAY;
        } else if (t.contains("float")) {
            return DbColumnType.FLOAT;
        } else if (t.contains("double")) {
            return DbColumnType.DOUBLE;
        }
        return DbColumnType.STRING;
    }
}