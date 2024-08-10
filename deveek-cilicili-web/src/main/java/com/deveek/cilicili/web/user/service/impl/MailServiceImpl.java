package com.deveek.cilicili.web.user.service.impl;

import com.deveek.cilicili.web.common.media.constant.MailProperties;
import com.deveek.cilicili.web.user.service.MailService;
import com.deveek.cilicili.web.common.user.constant.EmailConstant;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender javaMailSender;
    
    @Resource
    private MailProperties mailProperties;
    
    @Async("smsExecutorService")
    @Override
    public void sendVerifyCode(String verifyCode, String receiver) {
        sendContext(EmailConstant.SUBJECT, verifyCode, mailProperties.getUsername(), receiver);
    }
    
    @Async("smsExecutorService")
    public void sendContext(String subject, String content, String sender, String receiver) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setFrom(sender);
        javaMailSender.send(simpleMailMessage);
    }
}
