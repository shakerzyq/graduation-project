package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/17 23:45
 */
@Mapper
public interface MessageMapper {
    @Select("select * from comment where commented_userid=#{flea_id} and observer_id!=#{flea_id} order by put_time desc")
    ArrayList<Comment> selectCommentA(String flea_id);

    @Select("select * from comment_1 where commented_userid=#{flea_id} and observer_id!=#{flea_id} order by put_time desc")
    ArrayList<Comment> selectCommentB(String flea_id);

    @Update("update comment set view_status=1 where comment_id=#{id}")
    Boolean updateViewStatusA(String id);

    @Update("update comment_1 set view_status=1 where comment_id=#{id}")
    Boolean updateViewStatusB(String id);
}
