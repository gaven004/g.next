package com.g.config;

import java.text.SimpleDateFormat;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.g.commons.utils.DateTimePattern;
import com.g.commons.utils.MailSender;

@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat(DateTimePattern.DF_YYYY_MM_DD));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean
    @Qualifier("gsgdgcyxgs_pm@163.com")
    public JavaMailSender javaMailSender() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.163.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.debug", false);
        properties.put("mail.smtp.starttls.enable", true);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setUsername("gsgdgcyxgs_pm@163.com");
        javaMailSender.setPassword("abcde12345");

        return javaMailSender;
    }

    @Bean
    public MailSender mailSender(@Qualifier("gsgdgcyxgs_pm@163.com") JavaMailSender javaMailSender) {
        return new MailSender("gsgdgcyxgs_pm@163.com", javaMailSender);
    }
}
