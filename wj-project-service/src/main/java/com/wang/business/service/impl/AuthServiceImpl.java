package com.wang.business.service.impl;

import com.wang.business.service.AuthService;
import com.wang.business.utils.FieldsUtil;
import com.wang.common.constants.MQConstant;
import com.wang.common.constants.RedisConst;
import com.wang.common.enums.RedisEnum;
import com.wang.common.object.entity.MailStruct;
import com.wang.business.utils.CodeUtil;
import com.wang.common.utils.RedisUtil;
import com.wang.common.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private FieldsUtil fieldsUtil;

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void sendValidCodeByEmail(String toEmail) {
        String msgId = StrUtils.getUUID();
        MailStruct mailStruct = new MailStruct();
        mailStruct.setToAddr(toEmail);
        mailStruct.setTitle("你的验证码:");
        String validCode = codeUtil.getRandomValidCode();
        mailStruct.setContent(validCode);
        mailStruct.setMsgId(msgId);
        // 放在redis里面
        redisUtil.setExTime(RedisEnum.LOGIN_VALID_CODE.getRedisKey(RedisConst.SEGMENTATION,toEmail),validCode,60, TimeUnit.SECONDS);
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(MQConstant.DIRECT_EXCHANGE,MQConstant.EMAIL_ROUTINGKEY, mailStruct,correlationData);
    }


}
