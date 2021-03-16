package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * 喜欢数和收藏数
 * @author 周杨清
 * @date 2021/2/23 18:18
 */
@Component
public class LikeAndCollect {

    private Integer likeNum;
    private Integer collectNum;

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public String toString() {
        return "LikeAndCollect{" +
                "likeNum='" + likeNum + '\'' +
                ", collectNum='" + collectNum + '\'' +
                '}';
    }
}
