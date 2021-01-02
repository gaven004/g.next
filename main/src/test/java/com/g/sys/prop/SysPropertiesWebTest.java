package com.g.sys.prop;

import static org.hamcrest.CoreMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.g.WebApplicationTest;

public class SysPropertiesWebTest extends WebApplicationTest {
    @Test
    void find() throws Exception {
        mockMvc.perform(get("/sys/properties?status=VALID&sortOrder=0&sortOrder=10&page=0&size=10&sort=category,asc&sort=name,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", notNullValue()));
    }

    @Test
    public void update() throws Exception {
        final String value = "{\"category\":\"Test\",\"name\":\"Hi\",\"value\":\"World\",\"status\":\"VALID\"}";
        mockMvc.perform(put("/sys/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
