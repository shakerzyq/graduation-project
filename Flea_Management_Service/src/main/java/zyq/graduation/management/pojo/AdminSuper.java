package zyq.graduation.management.pojo;

import zyq.graduation.management.util.MakeMD5;

/**
 * @author 周杨清
 * @date 2021/4/24 17:24
 */
public class AdminSuper {
    private String account;
    private String password;
    private String name;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MakeMD5.makeMd5(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AdminSuper{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
