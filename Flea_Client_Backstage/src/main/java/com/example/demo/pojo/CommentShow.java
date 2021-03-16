package com.example.demo.pojo;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @author 周杨清
 * @date 2021/2/26 12:09
 */
@Component
public class CommentShow {

    private String user_id;
    private String comment_id;//评论id
    private String comment_f_id;//父级评论id
//    private String commented_id;
    private Integer comment_rank;//评论等级
//    private String commented_userid;
    private String commented_name;//被评论者用户名
    private String observer_name;//评论者昵称
    private String observer_icon;//评论者头像
    private String observer_id;//评论者id
    private String comment;//评论内容
    private LinkedList<CommentShow> child_comment;//子评论
    private Integer comment_like;//评论点赞数
    private Integer likeJudge;//评论判断
    private String put_time;//评论时间
    private Integer judgeLength;

    public Integer getJudgeLength() {
        return judgeLength;
    }

    public void setJudgeLength(Integer judgeLength) {
        this.judgeLength = judgeLength;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getLikeJudge() {
        return likeJudge;
    }

    public void setLikeJudge(Integer likeJudge) {
        this.likeJudge = likeJudge;
    }

    public String getComment_f_id() {
        return comment_f_id;
    }

    public void setComment_f_id(String comment_f_id) {
        this.comment_f_id = comment_f_id;
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

    public String getCommented_name() {
        return commented_name;
    }

    public void setCommented_name(String commented_name) {
        this.commented_name = commented_name;
    }

    public String getObserver_name() {
        return observer_name;
    }

    public void setObserver_name(String observer_name) {
        this.observer_name = observer_name;
    }

    public String getObserver_icon() {
        return observer_icon;
    }

    public void setObserver_icon(String observer_icon) {
        this.observer_icon = observer_icon;
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

    public LinkedList<CommentShow> getChild_comment() {
        return child_comment;
    }

    public void setChild_comment(LinkedList<CommentShow> child_comment) {

        this.child_comment = child_comment;
    }

    public Integer getComment_like() {
        return comment_like;
    }

    public void setComment_like(Integer comment_like) {
        this.comment_like = comment_like;
    }

    public String getPut_time() {
        return put_time;
    }

    public void setPut_time(String put_time) {
        this.put_time = put_time;
    }

    @Override
    public String toString() {
        return "CommentShow{" +
                "user_id='" + user_id + '\'' +
                ", comment_id='" + comment_id + '\'' +
                ", comment_id_f='" + comment_f_id + '\'' +
                ", comment_rank='" + comment_rank + '\'' +
                ", commented_name='" + commented_name + '\'' +
                ", observer_name='" + observer_name + '\'' +
                ", observer_icon='" + observer_icon + '\'' +
                ", observer_id='" + observer_id + '\'' +
                ", comment='" + comment + '\'' +
                ", child_comment=" + child_comment +
                ", comment_like=" + comment_like +
                ", likeJudge=" + likeJudge +
                ", put_time='" + put_time + '\'' +
                '}';
    }
}
