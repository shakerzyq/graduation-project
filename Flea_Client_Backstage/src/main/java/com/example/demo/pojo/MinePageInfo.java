package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 *
 * 我的界面信息的封装类
 * @author 周杨清
 * @date 2021/3/9 21:15
 */
@Component
public class MinePageInfo {

    private String userName;
    private String userIcon;
    private Double userCredit;

    private Integer collectNum;
    private Integer attentionNum;
    private Integer fansNum;
    private Integer historyNum;

    private String myGoods;
    private String soldGoods;
    private String myOrders;
    private String myBought;
    private String myDisputes;


    public Double getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Double userCredit) {
        this.userCredit = userCredit;
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

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(Integer attentionNum) {
        this.attentionNum = attentionNum;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getHistoryNum() {
        return historyNum;
    }

    public void setHistoryNum(Integer historyNum) {
        this.historyNum = historyNum;
    }

    public String getMyGoods() {
        return myGoods;
    }

    public void setMyGoods(String myGoods) {
        this.myGoods = myGoods;
    }

    public String getSoldGoods() {
        return soldGoods;
    }

    public void setSoldGoods(String soldGoods) {
        this.soldGoods = soldGoods;
    }

    public String getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(String myOrders) {
        this.myOrders = myOrders;
    }

    public String getMyBought() {
        return myBought;
    }

    public void setMyBought(String myBought) {
        this.myBought = myBought;
    }

    public String getMyDisputes() {
        return myDisputes;
    }

    public void setMyDisputes(String myDisputes) {
        this.myDisputes = myDisputes;
    }

    @Override
    public String toString() {
        return "MinePageInfo{" +
                "userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", collectNum=" + collectNum +
                ", attentionNum=" + attentionNum +
                ", fansNum=" + fansNum +
                ", historyNum=" + historyNum +
                ", myGoods='" + myGoods + '\'' +
                ", soldGoods='" + soldGoods + '\'' +
                ", myOrders='" + myOrders + '\'' +
                ", myBought='" + myBought + '\'' +
                ", myDisputes='" + myDisputes + '\'' +
                '}';
    }
}
