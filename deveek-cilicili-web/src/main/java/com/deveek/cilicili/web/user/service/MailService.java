package com.deveek.cilicili.web.user.service;

/**
 * @author harvey
 */
public interface MailService {
    void sendVerifyCode(String verifyCode, String receiver);
}
