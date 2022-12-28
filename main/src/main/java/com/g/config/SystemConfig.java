package com.g.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * 读入配置文件system.properties，用于非spring组件使用
 * <p>
 * 配置文件的查找位置顺序
 * <p>
 * A /config subdirectory of the current directory
 * The current directory
 * A classpath /config package
 * The classpath root
 */
public class SystemConfig {

    private static final Properties properties = new Properties();

    static {
        String[] locations = {"file:./config/", "file:./config/*/", "file:./", "classpath:/config/", "classpath:/"};
        String PROPERTIES_FILE = "system.properties";

        try {
            boolean done = false;
            DefaultResourceLoader loader = new DefaultResourceLoader();

            for (String prefix : locations) {
                Resource resource = loader.getResource(prefix + PROPERTIES_FILE);
                if (resource.exists()) {
                    properties.load(resource.getInputStream());
                    done = true;
                    break;
                }
            }

            if (!done) {
                throw new RuntimeException(PROPERTIES_FILE + " not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to read " + PROPERTIES_FILE);
        }
    }

    /**
     * Get a property value by name
     *
     * @param propertyName The name of the property
     * @return The value currently associated with that property name; may be null.
     */
    public static String getProperty(String propertyName) {
        Object o = properties.get(propertyName);
        if (o == null) {
            throw new IllegalArgumentException("property " + propertyName + " not found");
        }
        return o instanceof String ? (String) o : o.toString();
    }
}
