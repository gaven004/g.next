package com.g;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * in this test, Spring Boot instantiates only the web layer rather than the whole context
 *
 * We use @MockBean to create and inject a mock for the Service
 * (if you do not do so, the application context cannot start),
 * and we set its expectations using Mockito.
 *
 * https://spring.io/guides/gs/testing-web/
 */

@WebMvcTest
public abstract class WebLayerTest {
    @Autowired
    protected MockMvc mockMvc;

//    @MockBean
//    private GreetingService service;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }
}
