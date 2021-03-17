package com.example.demo.service.impl;

import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.LoginMapper;
import com.example.demo.mapper.RegisterMapper;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.AccountUsers;
import com.example.demo.service.RegisterService;
import com.example.demo.util.JedisPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;

@Service
public class IRegisterService implements RegisterService {

    private static String url="http://localhost:8091/test_images/";

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    RegisterMapper registerMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    ESDataDao esDataDao;

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @Override
    public Boolean sendAuthCode(String email) {
        //设置邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(email);
        String auth_code=(int)((Math.random()*9+1)*100000)+"";
        message.setText("您的跳蚤验证码为："+auth_code+" 有效时间为5分钟");
        //邮件要发给谁
        message.setTo(email);
        //是谁发的
        message.setFrom("1346083462@qq.com");
        mailSender.send(message);
        //将验证码存入redis中，并且定时
        //获取连接池
        Jedis jedis = JedisPoolUtils.getJedis();
        //操作redis
        jedis.setex(email,300,auth_code);
        //关闭连接池
        jedis.close();
        return true;
    }

    /**
     * 验证验证码
     * @param authCode
     * @param email
     * @param type
     * @return
     */
    @Override
    public String verifyCode(String authCode,String email,String type) {
        //获取缓存中的验证码
        Jedis jedis = JedisPoolUtils.getJedis();

        System.out.println("验证码是否正确");
        System.out.println("authCode为"+authCode);
        System.out.println("email:"+email);
        System.out.println("redis："+jedis.get(email));

        if (authCode.equals(jedis.get(email))) {
            if (type.equals("login")){

                return loginMapper.selectFealId(email);
            }
            return "success";
        }else{
            return "false";
        }
        //核对是否相等，并返回值

    }

    /**
     * 上传文件
     * @param file
     * @param type
     * @return
     */
    @Override
    public String upload(MultipartFile file,String type) {
        String url2 = url;
        if (file.isEmpty()) {
            System.out.println("上传失败，请选择文件");
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\test_images\\"+type+"\\";
        url2=url2+type+"/"+fileName;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            //LOGGER.info("上传成功");
            return url2;
        } catch (IOException e) {
            //LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }

    /**
     * 用户注册
     * @param accountUsers
     * @return
     */
    @Override
    public String registerUser(AccountUsers accountUsers) throws Exception{
        //生成跳蚤ID
        String flea_id = accountUsers.getAccount().getPassword().substring(0,9);
        accountUsers.getUser().setFlea_id(flea_id);
        accountUsers.getAccount().setFlea_id(flea_id);
        System.out.println("md5密码为"+accountUsers.getAccount().getPassword());
        System.out.println("跳蚤id："+flea_id);
        registerMapper.InsertAccount(accountUsers.getAccount());
        registerMapper.InsertUser(accountUsers.getUser());
        //将用户信息插入ES
        esDataDao.insertUser(accountUsers.getUser());


        return null;
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    @Override
    public Boolean verifyEmail(String email) {
        Account account =  registerMapper.SelectAccount(email);
        System.out.println("register——code：查询到的account为："+account);
        if (account!=null){
            System.out.println("true");
            return true;
        }else{
            System.out.println("false");
            return false;
        }
    }
}
