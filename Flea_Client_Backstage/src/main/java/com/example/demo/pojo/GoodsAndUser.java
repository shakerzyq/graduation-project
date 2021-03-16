package com.example.demo.pojo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 商品信息和用户信息，点赞信息，收藏信息的结合
 * 用于商品详细展示界面
 * @author 周杨清
 * @date 2021/2/23 16:25
 */
@Component
public class GoodsAndUser {

    //user相关
    private String userIcon;
    private String userName;
    private String userId;
    private String credit;

    //访问者相关
    private String observerName;
    private String observerIcon;

    //关注判断
    private Integer fansJudge;
    //商品收藏判断
    private Boolean goodsCollectJudge;
    //商品喜欢判断
    private Boolean goodsLikeJudge;
    //商品举报判断
    private Boolean goodsReportJudge;


    //商品信息
    private String goodsTitle;
    private String goodsDes;
    private Double goodsNowPrice;
    private Double goodsOldPrice;
    private String goodsClass;
    private String goodsQuality;
    private String brand;
    private String photos;
    private Integer commentNum;//评论条数
    private ArrayList<String> goodsReportContent;//商品举报内容
    private String atype;
    private String btype;
    private String ctype;
    private Long addTime;
    private String status;

    //点赞数，收藏数,浏览量
    private Integer goodsCollect;
    private Integer goodsLike;
    private Integer goodsViews;

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getGoodsReportJudge() {
        return goodsReportJudge;
    }

    public ArrayList<String> getGoodsReportContent() {
        return goodsReportContent;
    }

    public void setGoodsReportContent(ArrayList<String> goodsReportContent) {
        this.goodsReportContent = goodsReportContent;
    }

    public void setGoodsReportJudge(Boolean goodsReportJudge) {
        this.goodsReportJudge = goodsReportJudge;
    }

    public Boolean getGoodsCollectJudge() {
        return goodsCollectJudge;
    }

    public void setGoodsCollectJudge(Boolean goodsCollectJudge) {
        this.goodsCollectJudge = goodsCollectJudge;
    }

    public Boolean getGoodsLikeJudge() {
        return goodsLikeJudge;
    }

    public void setGoodsLikeJudge(Boolean goodsLikeJudge) {
        this.goodsLikeJudge = goodsLikeJudge;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer ommentNum) {
        this.commentNum = ommentNum;
    }

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }

    public String getObserverIcon() {
        return observerIcon;
    }

    public void setObserverIcon(String observerIcon) {
        this.observerIcon = observerIcon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGoodsViews() {
        return goodsViews;
    }

    public void setGoodsViews(Integer goodsViews) {
        this.goodsViews = goodsViews;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Integer getFansJudge() {
        return fansJudge;
    }

    public void setFansJudge(Integer fansJudge) {
        this.fansJudge = fansJudge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    public Double getGoodsNowPrice() {
        return goodsNowPrice;
    }

    public void setGoodsNowPrice(Double goodsNowPrice) {
        this.goodsNowPrice = goodsNowPrice;
    }

    public Double getGoodsOldPrice() {
        return goodsOldPrice;
    }

    public void setGoodsOldPrice(Double goodsOldPrice) {
        this.goodsOldPrice = goodsOldPrice;
    }

    public String getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(String goodsClass) {
        this.goodsClass = goodsClass;
    }

    public String getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(String goodsQuality) {
        this.goodsQuality = goodsQuality;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Integer getGoodsCollect() {
        return goodsCollect;
    }

    public void setGoodsCollect(Integer goodsCollect) {
        this.goodsCollect = goodsCollect;
    }

    public Integer getGoodsLike() {
        return goodsLike;
    }

    public void setGoodsLike(Integer goodsLike) {
        this.goodsLike = goodsLike;
    }

    @Override
    public String toString() {
        return "GoodsAndUser{" +
                "userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", observerName='" + observerName + '\'' +
                ", observerIcon='" + observerIcon + '\'' +
                ", fansJudge=" + fansJudge +
                ", goodsCollectJudge=" + goodsCollectJudge +
                ", goodsLikeJudge=" + goodsLikeJudge +
                ", goodsReportJudge=" + goodsReportJudge +
                ", goodsDes='" + goodsDes + '\'' +
                ", goodsNowPrice=" + goodsNowPrice +
                ", goodsOldPrice=" + goodsOldPrice +
                ", goodsClass='" + goodsClass + '\'' +
                ", goodsQuality='" + goodsQuality + '\'' +
                ", photos='" + photos + '\'' +
                ", commentNum=" + commentNum +
                ", goodsCollect=" + goodsCollect +
                ", goodsLike=" + goodsLike +
                ", goodsViews=" + goodsViews +
                '}';
    }
}
