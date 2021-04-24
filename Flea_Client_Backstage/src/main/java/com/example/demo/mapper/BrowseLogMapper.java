package com.example.demo.mapper;

import com.example.demo.pojo.BrowseLog;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/16 21:21
 */
@Mapper
public interface BrowseLogMapper {

    @Select("select count(*) from browse_log where goods_id=#{goodsId} and user_id=#{userId}")
    Integer selectBrowseNum(String goodsId, String userId);

/*    @Update("update into tb_browse_log (user_id,goods_id,browse_date,browse_num) values" +
            " (#{userId},#{goodsId},#{browseDate})")
    Boolean updateBrowseNun(String goodsId, String userId,String browseDate);*/
    @Update("update browse_log set browse_num=browse_num+1 where goods_id=#{goodsId} and user_id=#{userId}")
    Boolean updateBrowseNum(String goodsId, String userId);

    @Insert("insert into browse_log (browse_id,user_id,goods_id,browse_date,browse_num) values (#{browse_id},#{userId},#{goodsId},#{browse_date},0)")
    Boolean insertBrowseLog(String browse_id,String goodsId, String userId,Long browse_date);

    @Update("update browse_log set browse_date=#{currentTimeMillis} where goods_id=#{goodsId} and user_id=#{userId}")
    Boolean updateBrowseDate(long currentTimeMillis,String goodsId,String userId);

    @Select("select goods_id from browse_log where user_id=#{userId} order by browse_date desc")
    ArrayList<String> selectBrowseLog(String userId);

    @Delete("delete from browse_log where goods_id=#{goodsId}")
    void deleteBrowseLog(String goodsId);
}
