package com.wang.fanout.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutMQConfig {

    // 定义队列
    @Bean
    public Queue emailQueue(){
        return new Queue("email.fanout.queue",true);
    }

    @Bean
    public Queue smsQueue(){
        return new Queue("sms.fanout.queue",true);
    }

    @Bean
    public Queue wechatQueue(){
        return new Queue("wechat.fanout.queue",true);
    }


    // 定义交换机
    @Bean
    public DirectExchange fanoutExchange(){
        return new DirectExchange("fanout_order_exchange",true,false);
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange()).with("");
    }

    @Bean
    public Binding smsBinding(){
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange()).with("");
    }

    @Bean
    public Binding wechatBinding(){
        return BindingBuilder.bind(wechatQueue()).to(fanoutExchange()).with("");
    }


}
