package zyq.graduation.management.pojo;

import zyq.graduation.management.util.MakeMD5;

/**
 * @author 周杨清
 * @date 2021/3/30 17:50
 */
public class AdminGeneral {

    private String uuid;
    private String name;
    private String account;
    private String password;
    private String weixin;
    private String stu_num;
    private String province;
    private String college;
    private String area;
    private String picture;
    private String status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MakeMD5.makeMd5(password);
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    @Override
    public String toString() {
        return "AdminGeneral{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", weixin='" + weixin + '\'' +
                ", stu_num='" + stu_num + '\'' +
                ", province='" + province + '\'' +
                ", college='" + college + '\'' +
                ", area='" + area + '\'' +
                ", picture='" + picture + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public AdminGeneral() {
    }

    public AdminGeneral(String uuid, String name, String account, String password, String weixin, String stu_num, String province, String college, String area, String picture, String status) {
        this.uuid = uuid;
        this.name = name;
        this.account = account;
        this.password = password;
        this.weixin = weixin;
        this.stu_num = stu_num;
        this.province = province;
        this.college = college;
        this.area = area;
        this.picture = picture;
        this.status = status;
    }
}
