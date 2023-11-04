package com.wang.mq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleProduct {

    private static final String QUEUE_NAME="queue1";
    public void productRun(String[] args){
        // 创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置工厂参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        //Connection connection = null;
        //Channel channel = null;

//        try{
//            connection = factory.newConnection();
//            channel  = connection.createChannel();
//            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//            String message = "Hello this is RabbitMQ!";
//            channel.basicPublish("","queue1",null,message.getBytes());
//            System.out.println("消息发送成功!");
//        } catch(Exception e){
//            e.printStackTrace();
//            System.out.println("消息异常，"+e);
//        } finally{
//            if (channel!=null && channel.isOpen()){
//                try{
//                    channel.close();
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//            if (connection!=null && connection.isOpen()){
//                try{
//                    connection.close();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
        // try with resources
        try (Connection connection = factory.newConnection();
            Channel channel  = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = String.join("",args);
            channel.basicPublish("","queue1",null,message.getBytes());
            System.out.println("消息发送成功!"+message);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("消息异常，"+e);
        }
    }



    public static void main(String[] args) {
        SimpleProduct product = new SimpleProduct();
        product.productRun(args);
    }



}
