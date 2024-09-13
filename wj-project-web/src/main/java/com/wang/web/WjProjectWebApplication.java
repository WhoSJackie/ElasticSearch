package com.wang.web;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.Context;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@ServletComponentScan
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
