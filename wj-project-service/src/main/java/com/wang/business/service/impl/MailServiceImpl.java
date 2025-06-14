package com.wang.business.service.impl;

import com.rabbitmq.client.Channel;
import com.wang.business.service.MailService;
import com.wang.business.utils.MailUtil;
import com.wang.common.constants.MQConstant;
import com.wang.common.object.entity.MailStruct;
import com.wang.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
