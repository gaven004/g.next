package com.g.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    private ObjectMapperUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }
}
