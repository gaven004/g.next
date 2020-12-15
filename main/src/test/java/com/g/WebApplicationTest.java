package com.g;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * In this test, the full Spring application context is started but without the server.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WebApplicationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }
}
