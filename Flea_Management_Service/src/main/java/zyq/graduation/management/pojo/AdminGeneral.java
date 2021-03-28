package zyq.graduation.management.pojo;

/**
 * @author 周杨清
 * @date 2021/3/28 18:41
 */
public class AdminGeneral {
    private String account;
    private String password;

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
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminGeneral{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
