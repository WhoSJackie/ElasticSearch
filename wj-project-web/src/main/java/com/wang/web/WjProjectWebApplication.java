package com.wang.web;

import com.alibaba.cloud.nacos.NacosConfigManager;
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
@EnableFeignClients("com.wang.common.feign")
@ComponentScan(basePackages = {
        "com.wang.business.**.*",
        "com.wang.web.**.*",
        "com.wang.common.**.*"
})
public class WjProjectWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjProjectWebApplication.class, args);
    }

}
