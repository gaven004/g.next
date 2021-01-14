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

class GenericControllerTest extends WebApplicationTest {

    @Test
    void find() throws Exception {
        mockMvc.perform(get("/test/sys/properties?status=VALID&page=0&size=10&sort=category,asc&sort=name,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()));

        mockMvc.perform(get("/test/sys/properties?status=VALID&sort=category,asc&sort=name,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$", not(hasItem("pageSize"))));

        mockMvc.perform(get("/test/sys/properties?status=VALID")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$", not(hasItem("pageSize"))));
    }

    @Test
    void findAntd() throws Exception {
        mockMvc.perform(get("/test/sys/properties?current=1&pageSize=20&sorter=%7B%22category%22%3A%22ascend%22%7D&filter=%7B%7D")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()));

        mockMvc.perform(get("/test/sys/properties?status=VALID&sorter=%7B%22category%22%3A%22ascend%22%7D")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$", not(hasItem("pageSize"))));

        mockMvc.perform(get("/test/sys/properties?status=VALID")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$", not(hasItem("pageSize"))));
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