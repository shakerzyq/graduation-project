package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/6 21:20
 */
@Component
public class ESUser {
    private String userId;
    private String userName;
    private String userIcon;
    private Double userCredit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Double getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Double userCredit) {
        this.userCredit = userCredit;
    }

    @Override
    public String toString() {
        return "ESUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userCredit='" + userCredit + '\'' +
                '}';
    }
}
