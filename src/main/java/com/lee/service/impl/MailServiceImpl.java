package com.lee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import java.util.Random;

/**
 * 发送验证码邮件的服务,返回生成的验证码
 */
@Service
public class MailServiceImpl {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public String sendMail(String email) {
        //生成4位随机数作为验证码
        Random random = new Random();
        int captcha = random.nextInt(9000) + 1000;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置邮件标题和内容
        mailMessage.setSubject("欢迎注册WeiXin-Blog");
        mailMessage.setText("您的验证码是："+captcha);
        //设置源和发送邮箱地址
        mailMessage.setFrom("2567584274@qq.com");
        mailMessage.setTo(email);

        //发送邮件
        javaMailSender.send(mailMessage);

        return captcha+"";
    }

}
