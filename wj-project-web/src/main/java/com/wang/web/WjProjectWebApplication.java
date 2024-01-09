package com.wang.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.wang.business.*",
        "com.wang.web.*",
        "com.wang.common.*"
})
public class WjProjectWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjProjectWebApplication.class, args);
    }

}
