package com.deveek.common.support;

import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

/**
 * @author vocmi
 */
public class SendMailUtil {
    @Resource
    private JavaMailSender javaMailSender;

    /**
     *  发送邮件
     * @param messageHeader 消息标题
     * @param content 正文内容
     * @param sender 发送者
     * @param receiver 接收者
     */
    @Async("smsExecutor")
    public void sendSimpleMail(String messageHeader, String content, String sender, String receiver) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(messageHeader);
        simpleMailMessage.setText(content);
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setFrom(sender);
        javaMailSender.send(simpleMailMessage);
    }
}
