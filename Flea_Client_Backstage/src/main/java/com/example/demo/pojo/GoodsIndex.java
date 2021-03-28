package com.example.demo.pojo;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

/**
 * 对应ES索引
 * @author 周杨清
 * @date 2021/2/22 18:52
 */
@Component
public class GoodsIndex {

    private String userId;
    private String userName;
    private String userIcon;
    private Double userCredit;
    private String goodsTitle;
    private String goodsDes;
    private String goodsType;
    private String goodsPhoto;
    private String addPlace;
    private Double goodsPrice;
    private Integer goodsWanter;//收藏数
    private String goodsId;
    private Integer viewsNum;//浏览数，默认排序
    private Integer likesNum;//喜欢数
    private String status;

    public String getAddPlace() {
        return addPlace;
    }

    public void setAddPlace(String addPlace) {
        this.addPlace = addPlace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public void setGoodsWanter(Integer goodsWanter) {
        this.goodsWanter = goodsWanter;
    }

    public Integer getViewsNum() {
        return viewsNum;
    }

    public void setViewsNum(Integer viewsNum) {
        this.viewsNum = viewsNum;
    }

    public Integer getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
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

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsWanter() {
        return goodsWanter;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GoodsIndex{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userCredit=" + userCredit +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsDes='" + goodsDes + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", goodsPhoto='" + goodsPhoto + '\'' +
                ", addPlace='" + addPlace + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsWanter=" + goodsWanter +
                ", goodsId='" + goodsId + '\'' +
                ", viewsNum=" + viewsNum +
                ", likesNum=" + likesNum +
                ", status='" + status + '\'' +
                '}';
    }
}
