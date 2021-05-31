package com.example.demo.mapper;

import com.example.demo.pojo.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 周杨清
 * @date 2021/2/21 11:59
 */
@Mapper
@Component
public interface ForGoodsMapper {
    @Insert("insert into goods (product_id,merchant_id,product_title,product_des,quality,brand,atype,btype,ctype,old_price,now_price,photos,add_date,add_place" +
            ") values (#{goods.product_id},#{goods.merchant_id},#{goods.product_title},#{goods.product_des},#{goods.quality},#{goods.brand},#{goods.atype},#{goods.btype},#{goods.ctype},#{goods.old_price},#{goods.now_price},#{goods.photos},#{goods.add_date},#{goods.add_place})")
    Boolean InsertProduct(@Param("goods")Goods goods);

    //分页查询,商品信息
    @Select("select product_id,merchant_id,product_des,quality,class_choice,old_price,now_price,photos from goods " +
            "limit #{start},#{end}")
    ArrayList<Goods> selectGoods(Integer start,Integer end);

    //通过id查询用户信息
    @Select("select nickname,user_icon,credit from user where flea_id=#{UserId}")
    User SelectByUserId(String UserId);

    //查询关注信息
    @Select("select count(id) from fans where fans_id=#{fleaId} and fansed_id=#{merchantId}")
    Integer selectFans(String merchantId, String fleaId);

    //查询商品信息
    @Select("select * from goods where product_id = #{goodsId}")
    Goods slectGoodsByGoodsId(String goodsId);

    //查询商品的点赞信息
//    @Select("select count(l.id) likeNum,count(c.id) collectNum from collect c,likes l where c.product_id=#{goodsId} or l.product_id=#{goodsId}")
    @Select("select (select count(id) from collect where product_id=#{goodsId}) collectNum,(select count(id) from likes where product_id=#{goodsId}) likeNum")
    LikeAndCollect selectLikeByGoodsId(String goodsId);

    //更新商品浏览量
    @Update("update goods set  views=views+1 where product_id=#{goodsId} ")
    public void updateGoodsViews(String goodsId);

    //关注用户
    @Insert("insert into fans (fans_id,fansed_id) values (#{flea_id},#{merchant_id})")
    Boolean insertFans(String flea_id, String merchant_id);

    //取消关注
    @Delete("delete from fans where fans_id=#{flea_id} and fansed_id=#{merchant_id}")
    Boolean deleteFans(String flea_id, String merchant_id);

    @Select("select (select count(*) from comment where comment.goods_id=#{goodsId})+(select count(*) from comment_1 where comment_1.goods_id=#{goodsId})")
    Integer selectCommentNum(String goodsId);

    //商品点赞
    @Delete("delete from likes where consumer_id=#{flea_id} and product_id=#{goods_id}")
    Boolean deleteGoodsLike(String flea_id, String goods_id);

    @Insert("insert into likes (consumer_id,product_id,merchant_id,like_date) values (#{flea_id},#{goods_id},#{merchant_id},#{like_date})")
    Boolean insertGoodsLike(String flea_id, String goods_id,String merchant_id,Long like_date);

    //收藏相关
    @Delete("delete from collect where consumer_id=#{flea_id} and product_id=#{goods_id}")
    Boolean deleteGoodsCollect(String flea_id, String goods_id);

    @Insert("insert into collect (consumer_id,product_id,merchant_id) values (#{flea_id},#{goods_id},#{merchant_id})")
    Boolean insertGoodsCollect(String flea_id, String goods_id,String merchant_id);

    //查询用户对商品的点赞状态
    @Select("select count(id) from likes where consumer_id=#{fleaId} and product_id=#{goodsId}")
    Integer selectGoodsLikeJudge(String fleaId, String goodsId);

    //查询用户对商品的收藏状态
    @Select("select count(id) from collect where consumer_id=#{fleaId} and product_id=#{goodsId}")
    Integer selectGoodsCollectJudge(String fleaId, String goodsId);

    /*用户举报相关*/
    //删除举报
    @Delete("delete from violate_goods where informer_id=#{flea_id} and violate_goods_id=#{goods_id}")
    Boolean deleteGoodsReport(String flea_id, String goods_id);
    //插入举报
    @Insert("insert into violate_goods ( complain_id,informer_id,violate_goods_id,violate_user_id,violate_content,start_time,address) values (#{complain_id},#{flea_id},#{goods_id},#{merchant_id},#{report_content},now(),#{address})")
    Boolean insertGoodsReport(String complain_id,String flea_id, String goods_id, String merchant_id,String report_content,String address);

    //获取发布地址
    @Select("select concat(college,'-',area) from user where flea_id=#{userId}")
    String selectAddress(String userId);

    //更新举报关系
    @Update("update violate_goods set violate_content=#{violate_content} where informer_id=#{informer_id} and violate_goods_id=#{violate_goods_id}")
    Boolean updateGoodsReport(String informer_id, String violate_goods_id,String violate_content);

    //查询举报状态
    @Select("select count(*) from violate_goods where informer_id=#{fleaId} and violate_goods_id=#{goodsId}")
    Integer selectGoodsReportJudge(String fleaId, String goodsId);

    //查询商品举报内容
    @Select("select violate_content from violate_goods where violate_goods_id=#{goodsId} and informer_id=#{fleaId}")
    String selectReportContent(String fleaId, String goodsId);

    //查询单个商品内容--用于管理商品功能
    @Select("select * from goods where product_id=#{goodsId}")
    Goods selectGoodsByGoodsId(String goodsId);

    @Update("update goods set product_title=#{goods.product_title},product_des=#{goods.product_des}," +
            "quality=#{goods.quality},atype=#{goods.atype},btype=#{goods.btype}" +
            ",ctype=#{goods.ctype},brand=#{goods.brand},old_price=#{goods.old_price},now_price=#{goods.now_price}," +
            "photos=#{goods.photos} where product_id=#{goods.product_id}")
    Boolean updateGoodsInfo(@Param("goods") Goods goods);

    /**
     * 删除商品信息
     * @param goodsId
     * @return
     */
    @Delete("delete from goods where product_id=#{goodsId}")
    Boolean deleteGoodsInfo(String goodsId);

    @Delete("delete from comment where goods_id=#{goodsId}")
    void deleteCommentByGoodsId(String goodsId);

    @Delete("delete from comment_1 where goods_id=#{goodsId}")
    void deleteComment_1ByGoodsId(String goodsId);

    @Delete("delete from comment_likes where goods_id=#{goodsId}")
    void deleteComment_likeByGoodsId(String goodsId);

    /**
     * 查询用户是否对该商品下单
     * @param userid
     * @param goodsId
     * @return
     */
    @Select("select count(*) from orders where consumer_id=#{userid} and goods_id=#{goodsId}")
    Integer selectOrders(String userid, String goodsId);

    @Select("select concat(college,'-',area) from user where flea_id=#{userId}")
    String selectCollegeAreas(String userId);

    @Delete("delete from collect where product_id=#{goodsId}")
    Boolean deleteCollect(String goodsId);

    @Delete("delete from likes where product_id=#{goodsId}")
    Boolean deleteLikes(String goodsId);

    @Delete("delete from browse_log where goods_id=#{goodsId}")
    Boolean deleteLog(String goodsId);






    /*@Select("select user_icon userIcon,nickname userName from user where flea_id=#{merchantId} " +
            "union all " +
            "select product_des goodsDes,now_price goodsNowPrice,old_price goodsOldPrice,class_choice goodsClass,quality goodsQuality,photos from products where product_id=#{goodsId} " +
            " union all " +
            "select count(likes.id) goodsCollect from likes where product_id=#{goodsId} " +
            " union all " +
            "select count(collect.id) goodsLike from collect where product_id=#{goodsId}")
    ArrayList<GoodsAndUser> selectGoodsDetailInfo(String merchantId, String goodsId);*/



}
