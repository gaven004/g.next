package com.g.commons.utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import org.springframework.util.StringUtils;

import com.g.commons.enums.DescribableEnum;
import com.g.commons.enums.Option;

/**
 * 枚举工具类
 *
 * @author zhongsh
 * @version 2017/5/26
 */
public class EnumUtil {
    /**
     * 获取枚举的描述字符串
     *
     * @param enumType 枚举类型
     * @param name     枚举name
     * @return
     */
    public static <T extends Enum<T> & DescribableEnum> String getDescription(Class<T> enumType, String name) {
        if (enumType == null || !StringUtils.hasText(name)) {
            return null;
        }

        try {
            return Enum.valueOf(enumType, name).getDescription();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 根据描述字符串获取枚举
     *
     * @param enumType    枚举类型
     * @param description 枚举的描述字符串，如中文含义
     * @return
     */
    public static <T extends Enum<T> & DescribableEnum> T getEnum(Class<T> enumType, String description) {
        if (enumType == null || !StringUtils.hasText(description)) {
            return null;
        }

        EnumSet<T> set = EnumSet.allOf(enumType);
        for (T e : set) {
            if (description.equals(e.getDescription())) {
                return e;
            }
        }

        return null;
    }

    /**
     * 根据name获取枚举
     *
     * @param enumType 枚举类型
     * @param name     枚举name，如果为空返回null
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        if (enumType == null || !StringUtils.hasText(name)) {
            return null;
        }

        try {
            return Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 获取DescribableEnum的name:cname映射集合
     *
     * @param enumType 枚举类型
     * @return
     */
    public static <T extends Enum<T> & DescribableEnum> EnumMap<T, String> getDescMap(Class<T> enumType) {
        if (enumType == null) {
            return null;
        }

        EnumMap<T, String> map = new EnumMap<>(enumType);
        EnumSet<T> set = EnumSet.allOf(enumType);
        for (T e : set) {
            map.put(e, e.getDescription());
        }

        return map;
    }

    /**
     * 获取DescribableEnum的 value=name, label=cname的Option List
     *
     * @param enumType 枚举类型
     * @return
     */
    public static <T extends Enum<T> & DescribableEnum> List<Option> getDescOptions(Class<T> enumType) {
        if (enumType == null) {
            return null;
        }

        EnumSet<T> set = EnumSet.allOf(enumType);
        List<Option> list = new ArrayList<>(set.size());
        for (T e : set) {
            list.add(new Option(e.name(), e.getDescription()));
        }

        return list;
    }
}
