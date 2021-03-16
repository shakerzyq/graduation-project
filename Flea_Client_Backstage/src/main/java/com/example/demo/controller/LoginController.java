package com.example.demo.controller;

import com.example.demo.pojo.Account;
import com.example.demo.service.LoginService;
import com.example.demo.util.JedisPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 账号密码登录
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/pwdlogin")
    public String pwdLogin(@RequestBody Account account){
        System.out.println("密码为"+account);
        System.out.println("返回的值为"+loginService.verifyPwd(account));
        return loginService.verifyPwd(account);
    }

    /**
     * 修改密码
     *
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/pwdlogin/changePwd")
    public Boolean changePwd(@RequestBody Account account){
        System.out.println("密码为"+account);
        return loginService.changePwd(account);
    }



    /**
     *
     */
    @GetMapping("verifyKey/{email}/{loginKey}")
    public Boolean verifyLoginKey(@PathVariable("lgoinKey") String loginKey){
       return false;
    }
}
