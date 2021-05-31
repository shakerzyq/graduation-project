package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.Goods;
import zyq.graduation.management.pojo.GoodsReport;

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

    @Select("select concat(college,'-',area) from admin_general where account=#{adminAccount}")
    String selectAddressOfAdmin(String adminAccount);

    @Select("select * from violate_goods where address=#{address} and status=2 ")
    ArrayList<GoodsReport> selectGoodsReport(String address);

    @Select("select * from violate_goods where address=#{address} and status!=2 limit #{page},#{limit}")
    ArrayList<GoodsReport> selectAccomplishGoodsReport(Integer page, Integer limit,String address);

    @Update("update violate_goods set status=1,comment=#{content},end_time=now(),admin_id=#{adminAccount} where complain_id=#{reportId}")
    Boolean receiveGoodsReport(String reportId, String content, String adminAccount);

    @Update("update violate_goods set status=0,comment=#{content},end_time=now(),admin_id=#{adminAccount} where complain_id=#{reportId}")
    Boolean refuseGoodsReport(String reportId, String content, String adminAccount);

    @Select("select * from violate_goods where status=#{status} and address=#{address} limit #{page},#{limit}")
    ArrayList<GoodsReport> selectAccomplishReportStatus(int page, Integer limit, String status, String address);

    @Select("select * from violate_goods where status!=2 and (informer_id=#{userId} or violate_user_id=#{userId}) and address=#{address} limit #{page},#{limit}")
    ArrayList<GoodsReport> selectAccomplishReportById(int page, Integer limit, String userId, String address);

    @Select("select * from violate_goods where status=#{status} and (informer_id=#{userId} or violate_user_id=#{userId}) and address=#{address} limit #{page},#{limit}")
    ArrayList<GoodsReport> selectAccomplishReportByIdByStatus(int page, Integer limit, String userId, String status, String address);
}
