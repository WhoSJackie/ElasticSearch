package com.wang.business.service;

import com.rabbitmq.client.Channel;
import com.wang.common.object.entity.MailStruct;
import org.springframework.amqp.core.Message;

public interface MailService {

    void sendMail(Message message, Channel channel);

}
