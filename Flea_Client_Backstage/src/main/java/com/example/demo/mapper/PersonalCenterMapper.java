package com.example.demo.mapper;

import com.example.demo.pojo.Likes;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/13 0:07
 */
@Mapper
public interface PersonalCenterMapper {

    @Select("select count(*) from likes where merchant_id=#{userid}")
    Integer selectLikesNum(String userId);

    @Select("select nickname,user_icon,credit,self_introduction from user where flea_id=#{userid} ")
    User selectUserInfo(String userid);

    @Select("select * from likes where merchant_id=#{userId}")
    ArrayList<Likes> selectLikes(String userId);

    @Update("update account set email=#{email} where flea_id=#{userId}")
    Boolean updateUserEmail(String userId,String email);
}
