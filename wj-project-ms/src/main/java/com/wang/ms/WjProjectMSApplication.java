package com.wang.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ServletComponentScan
@ComponentScan(basePackages = {
        "com.wang.ms.**.*",
        "com.wang.common.**.*"})
public class WjProjectMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjProjectMSApplication.class, args);
    }

}
