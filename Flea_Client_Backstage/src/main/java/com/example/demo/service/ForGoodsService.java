package com.example.demo.service;

import com.example.demo.pojo.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 周杨清
 * @date 2021/2/21 11:46
 */
@Component
public interface ForGoodsService {
    Boolean putGoods(Goods goods) throws IOException;

    ArrayList<GoodsIndex> getGoods(Integer start,Integer end);

    GoodsAndUser s_GoodsDetailInfo(String merchantId,String fleaId,String goodsId);

    Integer s_fansAction(String feal_id, String merchan_id, String type);


    Boolean s_goodsLikeAction(String flea_id, String goods_id,String merchant_id,String type);

    Boolean s_goodsCollectAction(String flea_id, String goods_id,String merchant_id, String type);

    Boolean s_reportGoods(GoodsReport goodsReport,String type);


    Boolean s_unReportGoods(GoodsReport goodsReport);

    Goods s_getGoodsToUpdate(String goodsId);

    /**
     * 获取商品列表信息
     * @param from
     * @param size
     * @return
     */
    ArrayList<GoodsIndex> s_getGoodsForIndex(int from, int size,String status,String status2,String addPlace,String type);

    /**
     * 通过输入的字段查询商品--ES搜索
     * @param from
     * @param end
     * @param value
     * @return
     */
    ArrayList<GoodsIndex> s_searchGoodsList(int from, int end, String value);

    Boolean s_updateProduct(Goods goods);

    Boolean s_deleteProduct(String goodsId);

    Integer s_judgeGoodsToUser(String userid, String goodsId);
}
