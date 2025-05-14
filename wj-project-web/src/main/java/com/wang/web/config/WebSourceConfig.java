package com.wang.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSourceConfig implements WebMvcConfigurer {

    @Value("${prePicUrl}")
    private String prePicUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(prePicUrl+"**").addResourceLocations("file:D:/home/wjblog/");
    }
}
