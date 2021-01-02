package com.g.sys.prop;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.g.WebApplicationTest;

public class SysPropertyCategoriesWebTest extends WebApplicationTest {
    @Test
    void find() throws Exception {
        mockMvc.perform(get("/sys/property/categories?status=VALID&status=INVALID&page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", notNullValue()));
    }

    @Test
    public void update() throws Exception {
        final String value = "{\"id\":\"FILE_TYPE\",\"status\":\"VALID\"}";
        mockMvc.perform(put("/sys/property/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN")
                .content(value))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
