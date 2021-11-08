package com.g.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig {
    public WebConfig(@Autowired FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(Arrays.asList("/META-INF/security.tld"));
    }
}
