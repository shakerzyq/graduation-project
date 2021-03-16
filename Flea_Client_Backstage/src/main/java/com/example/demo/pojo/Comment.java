package com.example.demo.pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 评论表
 * @author 周杨清
 * @date 2021/2/23 15:23
 */
@Component
public class Comment implements Serializable {
    private String comment_id;
    private String commented_id;
    private String comment_f_id;
    private Integer comment_rank;
    private String goods_id;
    private String merchant_id;
    private String commented_userid;
    private String commented_username;
    private String observer_id;
    private String comment;

    private long put_time;

    public String getComment_f_id() {
        return comment_f_id;
    }

    public void setComment_f_id(String comment_f_id) {
        this.comment_f_id = comment_f_id;
    }

    public String getCommented_userid() {
        return commented_userid;
    }

    public void setCommented_userid(String commented_userid) {
        this.commented_userid = commented_userid;
    }

    public String getCommented_id() {
        return commented_id;
    }

    public void setCommented_id(String commented_id) {
        this.commented_id = commented_id;
    }

    public String getCommented_username() {
        return commented_username;
    }

    public void setCommented_username(String commented_username) {
        this.commented_username = commented_username;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public Integer getComment_rank() {
        return comment_rank;
    }

    public void setComment_rank(Integer comment_rank) {
        this.comment_rank = comment_rank;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getObserver_id() {
        return observer_id;
    }

    public void setObserver_id(String observer_id) {
        this.observer_id = observer_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPut_time() {
        return put_time;
    }

    public void setPut_time(long put_time) {
        this.put_time = put_time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id='" + comment_id + '\'' +
                ", commented_id='" + commented_id + '\'' +
                ", comment_f_id='" + comment_f_id + '\'' +
                ", comment_rank='" + comment_rank + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", commented_userid='" + commented_userid + '\'' +
                ", commented_username='" + commented_username + '\'' +
                ", observer_id='" + observer_id + '\'' +
                ", comment='" + comment + '\'' +
                ", put_time=" + put_time +
                '}';
    }
}
