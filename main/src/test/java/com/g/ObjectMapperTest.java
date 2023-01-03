package com.g;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g.commons.utils.ObjectMapperUtil;
import com.g.sys.sec.model.SysUser;

public class ObjectMapperTest {
    String text = "{\"id\":\"1\",\"account\":\"admin\",\"username\":\"系统管理员\",\"email\":\"admin@next.com\",\"roles\":[{\"id\":\"365090278861701120\",\"name\":\"\"},{\"id\":\"140228410537410560\",\"name\":\"\"}],\"status\":\"VALID\",\"oper\":\"edit\",\"_csrf\":\"219b2bf5-884b-4398-a5ce-26a2836af554\"}";
    @Test
    public void testUnmarshall() throws JsonProcessingException {
        ObjectMapper mapper = ObjectMapperUtil.getObjectMapper();
        SysUser user = mapper.readValue(text, SysUser.class);
        Assertions.assertNotNull(user);
        System.out.println("user = " + user);
    }
}
