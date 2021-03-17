package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author 周杨清
 * @date 2021/2/26 10:30
 */
@Mapper
@Component
public interface CommentsMapper {

    @Insert("insert into comment (comment_id,commented_id,comment_rank,goods_id,merchant_id,commented_userid,commented_username,observer_id,observer_username,comment,put_time) " +
            "values (#{comment_id},#{commented_id},#{comment_rank},#{goods_id},#{merchant_id},#{commented_userid},#{commented_username},#{observer_id},#{observer_username},#{comment},#{put_time})")
    Boolean insertComment(Comment comment);

    @Insert("insert into comment_1 (comment_id,commented_id,comment_f_id,comment_rank,goods_id,merchant_id,commented_userid,commented_username,observer_id,observer_username,comment,put_time) " +
            "values (#{comment_id},#{commented_id},#{comment_f_id},#{comment_rank},#{goods_id},#{merchant_id},#{commented_userid},#{commented_username},#{observer_id},#{observer_username},#{comment},#{put_time})")
    boolean insertComment_1(Comment comment);

    @Insert("insert into comment_relation (child_id,parent_id) values (#{comment_id},#{comment_f_id})")
    void InsertCommentRelation(String comment_id, String comment_f_id);

    @Select("select * from comment where goods_id=#{goodsid} limit #{start},#{num}")
    LinkedList<Comment> selectComments(String goodsid, int start, int num);

    @Select("select * from comment_1 where comment_f_id=#{comment_f_id} ")
    ArrayList<Comment> selectChildComments(String comment_f_id);

    @Select("select nickname from user where flea_id=#{userid}")
    String selectUserNameInfo(String userid);

    @Select("select user_icon from user where flea_id=#{userid}")
    String selectUserIconInfo(String userid);

    @Select("select count(id) from comment_likes where comment_id=#{comment_id}")
    Integer selectLikeNum(String comment_id);

    @Select("select child_id from comment_relation where parent_id=#{comment_id}")
    ArrayList<String> selectChilds(String comment_id);

    @Select("select parent_id from comment_relation where child_id=#{comment_id}")
    String selectFatherId(String comment_id);

    @Select("select count(id) from comment_likes where comment_id=#{comment_id} and consumer_id=#{fleaid}")
    Integer selectUserLike(String comment_id,String fleaid);

    @Delete("delete from comment_likes where comment_id=#{commentedid} and consumer_id=#{fledid} ")
    Boolean deleteCommentLike(String commentedid, String fledid,String goodsid);

    @Insert("insert into comment_likes (comment_id,consumer_id,goods_id) values (#{commentedid},#{fledid},#{goodsid})")
    Boolean insertCommentLike(String commentedid, String fledid,String goodsid);

    @Delete("delete from comment where comment_id=#{commentid}")
    Boolean deleteComment(String commentid);
    @Delete("delete from comment_1 where comment_id=#{commentid}")
    Boolean deleteComment1(String commentid);

    @Delete("delete from comment_1 where comment_f_id=#{commentid}")
    Boolean deleteChildComment(String commentid);
}
