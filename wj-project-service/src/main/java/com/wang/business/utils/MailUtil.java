package com.wang.business.utils;

import com.wang.common.object.entity.MailStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class MailUtil {

    @Value("${spring.mail.from}")
    private String fromAddr;

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMail(MailStruct mailStruct){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddr);
        simpleMailMessage.setTo(mailStruct.getToAddr());
        simpleMailMessage.setSubject(mailStruct.getTitle());
        simpleMailMessage.setText(mailStruct.getContent());
        try{
            mailSender.send(simpleMailMessage);
            log.info("发送邮件成功!");
            return true;
        } catch(Exception e){
            log.info("发送邮件失败!");
            return false;
        }
    }

}
