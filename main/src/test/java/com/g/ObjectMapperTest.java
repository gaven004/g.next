package com.g;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g.sys.sec.model.SysUser;

public class ObjectMapperTest {
    String text = "{\n" +
            "  \"id\": \"1\",\n" +
            "  \"account\": \"admin\",\n" +
            "  \"username\": \"系统管理员\",\n" +
            "  \"email\": \"admin@next.com\",\n" +
            "  \"roles\": [\n" +
            "    {\"id\": \"140228410537410560\", \"name\": \"系统管理员\", \"status\": \"VALID\", \"authorities\": []}\n" +
            "  ],\n" +
            "  \"status\": \"VALID\",\n" +
            "  \"oper\": \"edit\",\n" +
            "  \"_csrf\": \"a90b4953-7e45-4374-8899-b83725dab485\"\n" +
            "}\n";
    @Test
    public void testUnmarshall() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SysUser user = mapper.readValue(text, SysUser.class);
        Assertions.assertNotNull(user);
        System.out.println("user = " + user);
    }
}
