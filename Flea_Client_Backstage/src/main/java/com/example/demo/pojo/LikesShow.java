package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/13 3:57
 */
@Component
public class LikesShow {
    private String userId;
    private String userIcon;
    private String userName;

    private String goodsIcon;
    private String goodsId;

    private Long likeDate;

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

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public Long getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(Long likeDate) {
        this.likeDate = likeDate;
    }
}
