package com.g.sys.prop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.g.NextApplicationTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysPropertiesControllerTest {
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
        final String result = restTemplate.getForObject(
                "http://localhost:" + port + "/sys/properties?status=VALID&page=0&size=10&sort=category,asc&sort=name,asc",
                String.class);
        assertNotNull(result);
        assertTrue(result.contains("content"));
        System.out.println("result = " + result);
    }

    @Test
    void get() {
        final String result = restTemplate.getForObject(
                "http://localhost:" + port + "/sys/properties/Test/Hello",
                String.class);
        assertNotNull(result);
        System.out.println("result = " + result);
    }

    @Test
    void save() {
        final String value = "{\"category\":\"Test\",\"name\":\"Hi\",\"value\":\"Gaven\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(value, headers);
        final String result = restTemplate.postForObject(
                "http://localhost:" + port + "/sys/properties",
                request,
                String.class
        );
        assertNotNull(result);
        System.out.println("result = " + result);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
