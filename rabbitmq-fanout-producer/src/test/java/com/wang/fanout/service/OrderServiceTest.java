package com.wang.fanout.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void ContextLoads() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            Long userId = 1000L+i;
            Long productId = 100L+i;
            orderService.makeOrder(userId,productId,10);
        }

    }



}
