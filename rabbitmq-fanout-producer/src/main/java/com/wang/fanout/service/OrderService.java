package com.wang.fanout.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String exchangeName="fanout_order_exchange";

    private String routerKey = "";


    public void makeOrder(Long userId,Long productId,int num){
        String orderId = UUID.randomUUID().toString();

        // 根据productId查询商品库存

        // 库存足够，则下订单

        System.out.println("用户ID:"+userId+"--orderId:"+orderId);
        rabbitTemplate.convertAndSend(exchangeName,routerKey,orderId);
    }



}
