package com.wang.fanout.service;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@RabbitListener(bindings = @QueueBinding(
        value=@Queue(value = "sms.fanout.queue",autoDelete = "false"),
        exchange = @Exchange(value="fanout_order_exchange",type = ExchangeTypes.FANOUT)
))

@Service
public class SmsService {

    @RabbitHandler
    public void messageService(String message){
        // 发邮件
        System.out.println("Sms Message->"+message);
    }


}
