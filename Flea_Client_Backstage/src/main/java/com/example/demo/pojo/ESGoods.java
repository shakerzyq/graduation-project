package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/6 21:58
 */
@Component
public class ESGoods {
    private String userId;
    private String goodsTitle;
    private String goodsDes;
    private String goodsType;
    private String goodsPhoto;
    private Double goodsPrice;
    private Integer goodsWanter;//收藏数
    private String goodsId;
    private Integer viewsNum;//浏览数，默认排序
    private Integer likesNum;//喜欢数
    private String status;//订单状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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

    public void setGoodsWanter(Integer goodsWanter) {
        this.goodsWanter = goodsWanter;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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
}
