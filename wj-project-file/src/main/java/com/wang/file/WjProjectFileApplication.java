package com.wang.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@ServletComponentScan
//@EnableFeignClients("com.wang.common.feign")
@ComponentScan(basePackages = {
        "com.wang.common.**.*",
        "com.wang.file.**.*",})
public class WjProjectFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjProjectFileApplication.class, args);
    }

}
