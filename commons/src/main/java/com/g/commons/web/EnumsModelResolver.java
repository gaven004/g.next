package com.g.commons.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

@Component
public class EnumsModelResolver implements ModelResolver {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(EnumsModelResolver.class);

    private String type;
    private String name;

    @Override
    public void resolve(Model model) {
        Class<? extends Enum> clazz;
        try {
            clazz = (Class<? extends Enum>) Class.forName(type);
        } catch (ClassNotFoundException e) {
            logger.warn(e.getMessage());
            return;
        }

        if (!StringUtils.hasText(name)) {
            name = clazz.getSimpleName().toUpperCase();
        }

        try {
            Method method = clazz.getDeclaredMethod("getEnumSet");
            EnumSet<?> set = (EnumSet<?>) method.invoke(null);
            model.addAttribute(name, set);
            return;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.warn(e.getMessage());
        }

        EnumSet<?> set = (EnumSet<?>) EnumSet.allOf(clazz);
        model.addAttribute(name, set);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

}
