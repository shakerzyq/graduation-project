package com.example.demo.pojo;

import org.springframework.stereotype.Component;

import javax.websocket.Decoder;
import java.io.Serializable;
@Component
public class User implements Serializable {
    private String flea_id;
    private String sex;
    private String nickname;
    private String name;
    private String stu_num;
    private String province;
    private String college;
    private String academy;
    private String grade;
    private String user_icon;
    private String testify_img;
    private String self_introduction;
    private String status;
    private Double credit;
    private String weixin;

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFlea_id() {
        return flea_id;
    }

    public void setFlea_id(String flea_id) {
        this.flea_id = flea_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
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

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getTestify_img() {
        return testify_img;
    }

    public void setTestify_img(String testify_img) {
        this.testify_img = testify_img;
    }

    public String getSelf_introduction() {
        return self_introduction;
    }

    public void setSelf_introduction(String self_introduction) {
        this.self_introduction = self_introduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "User{" +
                "flea_id='" + flea_id + '\'' +
                ", sex='" + sex + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", stu_num='" + stu_num + '\'' +
                ", province='" + province + '\'' +
                ", college='" + college + '\'' +
                ", academy='" + academy + '\'' +
                ", grade='" + grade + '\'' +
                ", user_icon='" + user_icon + '\'' +
                ", testify_img='" + testify_img + '\'' +
                ", self_introduction='" + self_introduction + '\'' +
                ", status='" + status + '\'' +
                ", credit=" + credit +
                ", weixin='" + weixin + '\'' +
                '}';
    }
}
