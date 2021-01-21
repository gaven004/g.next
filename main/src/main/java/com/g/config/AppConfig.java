package com.g.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.g.commons.utils.ObjectMapperUtil;

@Configuration
// 强制先于DataSourceAutoConfiguration执行，以先创建ApplicationContextHolder
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ImportResource("classpath*:mail-config.xml")
public class AppConfig {
    @Bean
    // 强制先于DataSourceAutoConfiguration执行创建ApplicationContextHolder
    // 并不是ObjectMapper本身依赖
    @DependsOn("applicationContextHolder")
    public ObjectMapper objectMapper() {
        return ObjectMapperUtil.getObjectMapper();
    }
}
