package com.example.demo.pojo;


import com.example.demo.util.MakeMD5;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Account implements Serializable {

    private String flea_id;
    private String email;
    private String password;

    public String getFlea_id() {
        return flea_id;
    }

    public void setFlea_id(String flea_id) {
        this.flea_id = flea_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
//        String pwd2 = MakeMD5.makeMd5(password);
//        ObjectMapper mapper = new ObjectMapper();
//        Pwd pwd3 = mapper.readValue(pwd2,Pwd.class);
//        this.password = pwd3.getData();
        this.password =MakeMD5.makeMd5(password);
    }

    @Override
    public String toString() {
        return "Account{" +
                "flea_id='" + flea_id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
