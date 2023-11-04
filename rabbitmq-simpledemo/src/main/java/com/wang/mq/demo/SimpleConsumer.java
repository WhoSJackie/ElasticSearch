package com.wang.mq.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;

public class SimpleConsumer {

    public void consumerRun(){

        // 创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置工厂参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = null;
        Channel channel = null;

        try{
            connection = factory.newConnection();
            channel  = connection.createChannel();
            channel.queueDeclare("queue1",false,false,false,null);

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    System.out.println("标识 consumerTag: " + consumerTag);
//                    System.out.println("获取交换机信息: " + envelope.getExchange());
//                    System.out.println("获取路由key: " + envelope.getRoutingKey());
//                    System.out.println("获取 DeliveryTag: " + envelope.getDeliveryTag());
//                    System.out.println("配置信息 properties: " + properties);
                    System.out.println("接收队列的数据 body: " + new String(body));
                    doWork(new String(body));
                }
            };
            channel.basicConsume("queue1",true,consumer);

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("消息异常，"+e);
        }

    }

    private void doWork(String msg){
        char[] chs = msg.toCharArray();
        for (char ch : chs) {
            if (ch=='.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleConsumer consumer = new SimpleConsumer();
        consumer.consumerRun();
    }

}
