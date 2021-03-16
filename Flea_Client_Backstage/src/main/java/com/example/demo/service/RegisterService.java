package com.example.demo.service;

import com.example.demo.pojo.AccountUsers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface RegisterService {
    public Boolean sendAuthCode(String email);
    public String verifyCode(String authCode,String email,String type);
    public String upload(MultipartFile file,String type);

    public String registerUser(AccountUsers accountUsers) throws Exception;

    public Boolean verifyEmail(String email);
}
