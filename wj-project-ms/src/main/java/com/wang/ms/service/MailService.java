package com.wang.ms.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

public interface MailService {

    void sendMail(Message message, Channel channel);

}
