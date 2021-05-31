package com.example.demo.mapper;

import com.example.demo.pojo.User;
import com.example.demo.pojo.putpojo.UserComplain;
import com.example.demo.pojo.putpojo.UserComplainHistory;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/3/9 21:25
 */
@Mapper
public interface MinePageMapper {

    @Select("select * from user where flea_id=#{userid} ")
    User selectUserInfo(String userid);

    /**
     * 查询粉丝数
     * @param userid
     * @return
     */
    @Select("select count(*) from fans where fansed_id=#{userid}")
    Integer selectFansNum(String userid);

    /**
     * 查询收藏数
     * @param userid
     * @return
     */
    @Select("select count(*) from collect where consumer_id=#{userid}")
    Integer selectCollectNum(String userid);

    /**
     * 查询关注数
     * @param userid
     * @return
     */
    @Select("select count(*) from fans where fans_id=#{userid}")
    Integer selectAttentionNum(String userid);


    /**
     * 查询收藏的商品id
     * @param userId
     * @return
     */
    @Select("select product_id from collect where consumer_id=#{userId}")
    ArrayList<String> selectCollects(String userId);

    @Select("select fansed_id from fans where fans_id=#{userId}")
    ArrayList<String> selectAttentions(String userId);

    /**
     * 获取粉丝ID
     * @param userId
     * @return
     */
    @Select("select fans_id from fans where fansed_id=#{userId}")
    ArrayList<String> selectFans(String userId);

    @Insert("insert into violate_order (complain_id,order_id,goods_id,complain_userid,complained_userid,complain_content,complain_type,complain_photos,complain_status,start_time,address)" +
            " values (#{userComplain.complain_id},#{userComplain.order_id},#{userComplain.goods_id},#{userComplain.complain_userid},#{userComplain.complained_userid},#{userComplain.complain_content},#{userComplain.complain_type},#{userComplain.complain_photos},2,now(),#{userComplain.address})")
    Boolean insertComplain(@Param("userComplain") UserComplain userComplain);


    @Select("select * from violate_order where complain_status=#{status} and (complain_userid=#{userId} or complained_userid=#{userId})")
    ArrayList<UserComplain> selectUserComplain(String userId,String status);

    @Select("select * from violate_order where complain_status!=#{status} and (complain_userid=#{userId} or complained_userid=#{userId})")
    ArrayList<UserComplain> selectUserComplainFinished(String userId, String status);

    @Select("select * from violate_order where complain_id=#{complain_id} and complain_status=#{status}")
    UserComplain selectDetailComplain(String complain_id,String status);

    @Select("select * from violate_order where complain_id=#{complain_id} and complain_status=#{status}")
    UserComplain selectHistoryDetailComplain(String complain_id,String status);

    @Delete("delete from violate_order where complain_id=#{complain_id}")
    Boolean deleteComplain(String complain_id);

    @Update("update user set user_icon=#{user.user_icon},nickname=#{user.nickname},weixin=#{user.weixin},self_introduction=#{user.self_introduction} where flea_id=#{user.flea_id}")
    Boolean updateUserInfo(@Param("user") User user);

    @Select("select count(*) from browse_log where user_id=#{user_id}")
    Integer selectHistoryNum(String userid);

    @Select("select email from account where flea_id=#{userid}")
    String selectUserEmail(String userid);

    @Select("select concat(college,'-',area) from user where flea_id=#{complain_userid}")
    String selectAddress(String complain_userid);
}
