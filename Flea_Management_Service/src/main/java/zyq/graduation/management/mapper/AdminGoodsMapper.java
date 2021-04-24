package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.Goods;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/22 18:43
 */
@Mapper
public interface AdminGoodsMapper {

    @Select("select * from goods where product_id=#{goodsId}")
    Goods selectGoodsByGoodsId(String goodsId);

    @Update("update goods set status=#{status} where merchant_id=#{flea_id} and (status=#{nowStatus} or status=#{nowStatus2})")
    Boolean updateGoodsFreeze(String flea_id,Integer status,Integer nowStatus,Integer nowStatus2);

    @Update("update goods set status=#{status} where merchant_id=#{flea_id} and status=#{nowStatus}")
    Boolean updateGoodsUnFreeze(String flea_id,Integer status,Integer nowStatus);

    @Select("select product_id from goods where merchant_id=#{flea_id} and status=#{status}")
    ArrayList<String> selectGoodsByUserId(String flea_id,Integer status);
}
