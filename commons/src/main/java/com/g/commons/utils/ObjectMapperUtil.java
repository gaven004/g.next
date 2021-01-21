package com.g.commons.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat(DateTimePattern.DF_YYYY_MM_DD));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private ObjectMapperUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }
}
