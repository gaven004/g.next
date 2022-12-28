package com.g.commons.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.g.commons.model.ApiResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.exception.ErrorCode;
import com.g.commons.utils.EnumUtil;

@RestController
@RequestMapping(value = "/enums")
public class EnumsController implements InitializingBean {
    public static final String PACKAGE_SEARCH_PATH = "classpath*:com/g/**/*.class";

    private final MetadataReaderFactory metadataReaderFactory;

    private final Map<String, String> NAME_MAP;
    private final Map<String, Class> CLASS_MAP;

    @Value(PACKAGE_SEARCH_PATH)
    Resource[] resources;

    @Autowired
    public EnumsController(MetadataReaderFactory metadataReaderFactory) {
        this.metadataReaderFactory = metadataReaderFactory;
        NAME_MAP = new HashMap<>(32);
        CLASS_MAP = new HashMap<>(32);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (resources == null || resources.length == 0) {
            return;
        }

        for (Resource resource : resources) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            Class clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
            if (clazz.isEnum()) {
                NAME_MAP.put(clazz.getSimpleName(), clazz.getName());
                CLASS_MAP.put(clazz.getName(), clazz);
            }
        }
    }

    @GetMapping(value = "/{type}")
    public ApiResponse<?> getOptions(@PathVariable String type) {
        return Optional.ofNullable((type.indexOf('.') >= 0) ? type : NAME_MAP.get(type))
                .map(fullname -> CLASS_MAP.get(fullname))
                .map(clazz -> ApiResponse.success(EnumUtil.getDescOptions(clazz)))
                .orElse(ApiResponse.error(ErrorCode.EntityNotFound.code(), "没有指定的枚举类"));
    }
}
