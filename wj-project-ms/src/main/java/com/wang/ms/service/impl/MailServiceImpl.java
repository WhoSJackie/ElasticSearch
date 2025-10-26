package com.wang.ms.service.impl;

import com.rabbitmq.client.Channel;
import com.wang.common.constants.MQConstant;
import com.wang.common.object.entity.MailStruct;
import com.wang.common.utils.JsonUtil;
import com.wang.ms.service.MailService;
import com.wang.ms.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    MailUtil mailUtil;

    @RabbitListener(queues = {MQConstant.EMAIL_QUEUE})
    @Override
    public void sendMail(Message message, Channel channel) {
        String strBody = new String(message.getBody());
        System.out.println("转换后的值:"+strBody);
        MailStruct mailStruct = JsonUtil.parseJson(strBody, MailStruct.class);
        mailUtil.sendMail(mailStruct);
    }
}
