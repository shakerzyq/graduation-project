package com.example.demo.controller;

import com.example.demo.util.JedisPoolUtils;
import com.example.demo.util.SendEmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

/**
 * 消息通知
 * @author 周杨清
 * @date 2021/3/17 14:18
 */
@RestController
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    SendEmailMessage sendEmailMessage;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping("/sendemail/{email}/{content}")
    public Boolean sendEmailMessage(
            @PathVariable("email") String email
            ,@PathVariable("content") String content){
        System.out.println(email+content);
        return sendEmailMessage.sendMessage(email,content);
    }
}
