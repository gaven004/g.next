package com.g.sys.prop;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysPropertyCategoriesControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(restTemplate);
    }

    @Test
    void find() {
    }

    @Test
    void get() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
        final String value = "{\"id\":\"FILE_TYPE\",\"status\":\"VALID\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN");

        HttpEntity<String> request = new HttpEntity<>(value, headers);

        final String result = restTemplate.postForObject(
                "http://localhost:" + port + "/sys/property/categories",
                request,
                String.class
        );

        assertNotNull(result);
        System.out.println("result = " + result);
    }

    @Test
    void delete() {
    }
}
