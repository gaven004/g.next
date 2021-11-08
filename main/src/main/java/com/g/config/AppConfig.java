package com.g.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.g.commons.utils.ObjectMapperUtil;

@Configuration
@ImportResource("classpath*:sys-config.xml")
// 强制先于DataSourceAutoConfiguration执行，以先创建ApplicationContextHolder
// 强制先于WebMvcAutoConfiguration执行，以先创建ObjectMapper
@AutoConfigureBefore({DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class AppConfig {
    @Bean
    // 强制先于DataSourceAutoConfiguration执行创建ApplicationContextHolder
    // 并不是ObjectMapper本身依赖
    @DependsOn("applicationContextHolder")
    public ObjectMapper objectMapper() {
        // 采用统一配置的ObjectMapper
        return ObjectMapperUtil.getObjectMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public FreeMarkerConfigurer freemarkerConfig() {
//        var freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/");
//        return freeMarkerConfigurer;
//    }
}
