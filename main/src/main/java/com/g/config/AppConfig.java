package com.g.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g.commons.utils.ObjectMapperUtil;

@Configuration
@ImportResource("classpath*:sys-config.xml")
@AutoConfigureBefore({DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class AppConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // 采用统一配置的ObjectMapper
        return ObjectMapperUtil.getObjectMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
