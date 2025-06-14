package com.wang.business.config;

import com.wang.common.constants.MQConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue getEmailQueue(){
        return new Queue(MQConstant.EMAIL_QUEUE,true);
    }
    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(MQConstant.DIRECT_EXCHANGE,true,false);
    }
    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(getEmailQueue()).to(getDirectExchange()).with(MQConstant.EMAIL_ROUTINGKEY);
    }
    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate buildTemplate(){
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

}
