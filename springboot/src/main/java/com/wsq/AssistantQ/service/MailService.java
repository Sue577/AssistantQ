package com.wsq.AssistantQ.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author WSQ
 * @date 2018-04-28 19:47
 */
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendAttachmentsMail() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("发送方邮箱");
        helper.setTo("wcy623209668@qq.com");
        helper.setSubject("Test");
        helper.setText("Test");
        javaMailSender.send(mimeMessage);
    }
}
