package com.example.demo.controller;

import com.example.demo.mapper.RegisterMapper;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.AccountUsers;
import com.example.demo.pojo.College;
import com.example.demo.pojo.User;
import com.example.demo.pojo.register.CollegeAreas;
import com.example.demo.service.RegisterService;
import com.example.demo.util.JedisPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.xml.ws.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @Autowired
    RegisterMapper registerMapper;

    /**
     * 发送邮箱验证码
     * @param email
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/putemail/{type}/{email}")
    public Boolean receiveEmail(@PathVariable("email") String email,@PathVariable("type") String type){
        System.out.println("emailWie："+email+" type为："+type);
        //查询该用户是否已被注册
        if (registerService.verifyEmail(email)){//存在账户
            //该邮箱已被注册
            System.out.println("type为："+type);
            String[] uuid = type.split("_");
            if (type.equals("login")||type.equals("topwd")){
                //Jedis jedis = JedisPoolUtils.getJedis();
                return registerService.sendAuthCode(email);
                /*if (jedis.get(email+"logingkey")==null||jedis.get(email+"logingkey").length()==0){
                    jedis.set(email)
                }*/
//                if (jedis.get(email+"logingkey").equals(uuid[1])){
//                    return registerService.sendAuthCode(email);
//                }else{
//                    return null;
//                }

            }else
                return false;
            /*String[] uuid = type.split("_");
            if (type.equals(uuid[0])){
                Jedis jedis = JedisPoolUtils.getJedis();
                *//*if (jedis.get(email+"logingkey")==null||jedis.get(email+"logingkey").length()==0){
                    jedis.set(email)
                }*//*
                if (jedis.get(email+"logingkey").equals(uuid[1])){
                    return registerService.sendAuthCode(email);
                }else{
                    return null;
                }

            }else
                return false;*/
        }else{//不存在账户
            if (type.equals("register")){
                System.out.println("进入了register");
                return registerService.sendAuthCode(email);
            }else {
                return false;
            }
        }
    }

    /**
     * 验证验证码
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/email/{authCode}/{email}/{type}")
    public String verifyCode(@PathVariable("authCode") String authCode,@PathVariable("email") String email,@PathVariable("type") String type){
        String reuslt =  registerService.verifyCode(authCode,email,type);
        System.out.println("验证结果 "+reuslt);
        return reuslt;
    }
    /**
     * 通过选择的省份搜索大学
     * @param province
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/college/{province}")
    public List<CollegeAreas> PictureTypes(@PathVariable("province") String province){
        ArrayList<String> colleges = registerMapper.getColleges(province);
        ArrayList<CollegeAreas> collegeAreasArrayList = new ArrayList<>();
        String [] strArray=null;

        for (String str:colleges){
            strArray =str.split(",");
            CollegeAreas collegeAreas = new CollegeAreas();
            collegeAreas.setCollege(strArray[0]);
            collegeAreas.setArea(Arrays.asList(strArray[1].split("-")));
            collegeAreasArrayList.add(collegeAreas);
        }
        return collegeAreasArrayList;
    }
    /**
     * 实现简单的文件上传
     * @param file
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/upload/{type}")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@PathVariable("type") String type){
        System.out.println("商品图片上传,上传类型为："+type);
        String result = registerService.upload(file,type);
        System.out.println(result);
        return result;
    }

    /**
     * 上传注册信息
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/putInfo")
    public Boolean registerUser(@RequestBody AccountUsers accountUsers) throws Exception{
        System.out.println("收到消息"+accountUsers);
        System.out.println("用户头像为："+accountUsers.getUser().getUser_icon());
        return registerService.registerUser(accountUsers);
    }

    /**
     * 验证验证码
     * @param email
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/verifyEmail/{email}/{userid}")
    public Boolean verifyEmail(@PathVariable("email") String email){
        return registerService.verifyEmail(email);
    }
}
