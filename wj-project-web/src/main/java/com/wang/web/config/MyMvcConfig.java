//package com.wang.web.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//// @Configuration
//public class MyMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    MyCustomIntercepter customIntercepter;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(customIntercepter).addPathPatterns("/**");
//    }
//}
