package zyq.graduation.management.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/17 14:21
 */
@Component
public class SendEmailMessage {



    @Autowired
    JavaMailSenderImpl mailSender;

    public Boolean sendMessage(String email,String content) {
        //设置邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(email);
        String auth_code=(int)((Math.random()*9+1)*100000)+"";
        message.setText(content);
        //邮件要发给谁
        message.setTo(email);
        //是谁发的
        message.setFrom("1346083462@qq.com");
        mailSender.send(message);
        //将验证码存入redis中，并且定时
        return true;
    }
}
