package com.g.commons.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.g.WebApplicationTest;

class GeneralControllerTest extends WebApplicationTest {

    @Test
    void find() throws Exception {
        mockMvc.perform(get("/test/sys/properties?status=VALID&page=0&size=10&sort=category,asc&sort=name,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("SUCCESS")))
                .andExpect(jsonPath("$.body.content", notNullValue()));

        mockMvc.perform(get("/test/sys/properties?status=VALID&sort=category,asc&sort=name,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("SUCCESS")))
                .andExpect(jsonPath("$.body", notNullValue()))
                .andExpect(jsonPath("$.body", not(hasItem("pageable"))));

        mockMvc.perform(get("/test/sys/properties?status=VALID")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("SUCCESS")))
                .andExpect(jsonPath("$.body", notNullValue()))
                .andExpect(jsonPath("$.body", not(hasItem("pageable"))));
    }

    @Test
    void save() throws Exception {
        final String value = "{\"category\":\"Test4\",\"name\":\"Hi\",\"value\":\"World\",\"status\":\"VALID\"}";
        mockMvc.perform(post("/test/sys/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void update() {
    }
}