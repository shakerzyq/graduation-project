package com.example.demo.service.impl;

import com.example.demo.mapper.LoginMapper;
import com.example.demo.pojo.Account;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 周杨清
 * @date 2021/2/19 12:34
 */
@Service
public class ILoginService implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    Account account;

    @Override
    public String verifyPwd(Account account) {

        System.out.println("password："+account.getPassword());
        Account account2 = loginMapper.selectEmailByPwd(account);
        System.out.println("查询后的account为"+account2);
        if(account2==null){
            System.out.println("账号密码错误");
            return null;
        }
        System.out.println("success");
        return account2.getFlea_id();
    }

    @Override
    public Boolean changePwd(Account account) {
        return loginMapper.updatePwd(account.getEmail(),account.getPassword());

    }
}
