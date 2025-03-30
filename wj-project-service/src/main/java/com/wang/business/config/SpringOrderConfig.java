package com.wang.business.config;

import com.wang.business.service.test.TestSpringOrder;
import com.wang.business.service.test.TestSpringOrder2;
import org.springframework.context.annotation.Bean;


//@Configuration
public class SpringOrderConfig {

    @Bean(initMethod = "initMethod")
    public static TestSpringOrder setSpringOrder(){
        return new TestSpringOrder();
    }

    @Bean
    public static TestSpringOrder2 setSpringOrder2(){
        return new TestSpringOrder2();
    }

}
