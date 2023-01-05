package com.g.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g.commons.utils.MailSender;
import com.g.commons.utils.ObjectMapperUtil;

@Configuration
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

    @Bean
    public MailSender usrMailSender(JavaMailSender mailSender) {
        Assert.notNull(mailSender, "A JavaMailSender must be set");
        return new MailSender("gsgdgcyxgs_pm@163.com", mailSender);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:org/springframework/security/messages");
        return messageSource;
    }
}
