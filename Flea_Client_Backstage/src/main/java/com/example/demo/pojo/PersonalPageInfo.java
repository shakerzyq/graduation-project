package com.example.demo.pojo;

/**
 * @author 周杨清
 * @date 2021/3/12 23:15
 */
public class PersonalPageInfo {

    private String userIcon;
    private String userName;
    private Double userCredit;
    private String userIntroduce;

    private Integer fansNum;
    private Integer attentionNum;
    private Integer likesNum;
    private Integer collectNum;

    private Integer tradingNum;
    private Integer deadNum;
    private Integer fansJudge;

    public Integer getFansJudge() {
        return fansJudge;
    }

    public void setFansJudge(Integer fansJudge) {
        this.fansJudge = fansJudge;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Double userCredit) {
        this.userCredit = userCredit;
    }

    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(Integer attentionNum) {
        this.attentionNum = attentionNum;
    }

    public Integer getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getTradingNum() {
        return tradingNum;
    }

    public void setTradingNum(Integer tradingNum) {
        this.tradingNum = tradingNum;
    }

    public Integer getDeadNum() {
        return deadNum;
    }

    public void setDeadNum(Integer deadNum) {
        this.deadNum = deadNum;
    }
}
