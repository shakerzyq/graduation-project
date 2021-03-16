package com.example.demo.service;

import com.example.demo.pojo.Account;
import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/2/19 12:34
 */
@Component
public interface LoginService {
    String verifyPwd(Account account);

    Boolean changePwd(Account account);
}
