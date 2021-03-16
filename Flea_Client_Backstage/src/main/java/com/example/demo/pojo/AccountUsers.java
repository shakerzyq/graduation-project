package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * 结合类，注册返回的account，user信息
 */
@Component
public class AccountUsers {

    private Account account;
    private User user;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AccountUsers{" +
                "account=" + account +
                ", user=" + user +
                '}';
    }
}
