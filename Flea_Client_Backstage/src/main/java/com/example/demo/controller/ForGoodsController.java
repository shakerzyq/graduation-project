package com.example.demo.controller;

import com.example.demo.dao.ESDataDao;
import com.example.demo.pojo.*;
import com.example.demo.service.impl.IForGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/2/21 11:41
 */
@RestController
@RequestMapping("/product")
public class ForGoodsController {

    @Autowired
    IForGoodsService iForGoodsService;

    //上架商品
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/upload")
    public Boolean putProduct(@RequestBody Goods goods) throws IOException {
        System.out.println("收到的products信息为："+goods);
        return iForGoodsService.putGoods(goods);
    }



    /**
     *  获取商品信息，用于更新商品
     * @param goodsId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/update/getGoods/{goodsId}")
    public Goods getGoodsToUpdate(@PathVariable("goodsId") String goodsId){
        System.out.println("收到的products信息为："+goodsId);
        return iForGoodsService.s_getGoodsToUpdate(goodsId);

    }


    /**
     * 更新商品
     * @param goods
     *
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/update")
    public Boolean updateProduct(@RequestBody Goods goods) throws IOException {
        System.out.println("收到的products信息为："+goods);
        return iForGoodsService.s_updateProduct(goods);
    }

    /**
     * 删除商品
     * @param goodsId
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/delete/{goodsId}")
    public Boolean deleteProduct(@PathVariable("goodsId") String goodsId) throws IOException {
        System.out.println("收到的products信息为："+goodsId);
        return iForGoodsService.s_deleteProduct(goodsId);
    }




    /**
     * 上传商品图片
     *
     * 此处使用的是RegisterController中的上传代码
     */



    /**
     * 显示商品列表
     * 查询ES中的数据，封装到ArrayList<GoodsIndex>中
     * 使用一个组合POJO，满足商品显示的需要
     * @param flea_id：为以后的个性化推荐做准备
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getgoodslist/{pagenum}/{flea_id}")
    public ArrayList<GoodsIndex> getGoodsForIndex(@PathVariable("pagenum")String pagenum, @PathVariable("flea_id") String flea_id){
        System.out.println("获取跳蚤id为："+flea_id+" pagenum为："+pagenum);
        //获取所有商品
//        ArrayList<GoodsIndex> result = iForGoodsService.getGoods(((Integer.parseInt(pagenum)-1)*8),8);

        ArrayList<GoodsIndex> result = iForGoodsService.s_getGoodsForIndex(((Integer.parseInt(pagenum)-1)*8),8,"dead");
        System.out.println(result);
        return result;
    }



    /**
     * 获取商品，用户详细信息
     * @param merchantId
     * @param goodsId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsDetail/{merchantId}/{goodsId}/{fleaId}")
    public GoodsAndUser goodsDetailInfo(@PathVariable("merchantId") String merchantId,@PathVariable("goodsId") String goodsId,@PathVariable("fleaId") String fleaId){
        System.out.println("传过来的goodsID，fleaId为："+goodsId+"  "+fleaId);
        GoodsAndUser result = iForGoodsService.s_GoodsDetailInfo(merchantId,fleaId,goodsId);
        return result;
    }

    /**
     * 用户关注相关
     * @param flea_id
     * @param merchan_id
     * @param type  fans关注  unfans取关
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/fansAction/{flea_id}/{merchant_id}/{type}")
    public Boolean fansAction(@PathVariable("flea_id") String flea_id,@PathVariable("merchant_id") String merchan_id,@PathVariable("type") String type) {
        System.out.println("feal_id:"+flea_id+" merchant_id"+merchan_id+" type:"+type);

        return iForGoodsService.s_fansAction(flea_id, merchan_id, type) == 1;
    }

    /**
     * 商品喜欢
     * @param flea_id
     * @param goods_id
     * @param type
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/goodsLike/{flea_id}/{goods_id}/{merchant_id}/{type}")
    public Boolean goodsLikeAction(@PathVariable("flea_id") String flea_id,@PathVariable("goods_id") String goods_id,@PathVariable("merchant_id") String merchant_id,@PathVariable("type") String type ){
        System.out.println("flea_id"+flea_id+"goods_id"+goods_id+"type:"+type);
        return iForGoodsService.s_goodsLikeAction(flea_id,goods_id,merchant_id,type);
    }

    /**
     * 商品收藏
     * @param flea_id
     * @param goods_id
     * @param merchant_id
     * @param type
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/goodsCollect/{flea_id}/{goods_id}/{merchant_id}/{type}")
    public Boolean goodsCollectAction(@PathVariable("flea_id") String flea_id,@PathVariable("goods_id") String goods_id ,@PathVariable("merchant_id") String merchant_id,@PathVariable("type") String type ){
        System.out.println("flea_id"+flea_id+"goods_id"+goods_id+"type:"+type);
        return iForGoodsService.s_goodsCollectAction(flea_id,goods_id,merchant_id,type);
    }

    /**
     * 举报/修改商品
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/goodsReport/{type}")
    public Boolean reportGoods(@RequestBody GoodsReport goodsReport,@PathVariable("type") String type){
        System.out.println(" goodsReport为"+goodsReport+" type为："+type);
        return iForGoodsService.s_reportGoods(goodsReport,type);
    }

    /**
     *取消举报
     * @param goodsReport
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/goodsUnReport")
    public Boolean reportGoods(@RequestBody GoodsReport goodsReport){
        System.out.println(" goodsReport"+goodsReport);
        return iForGoodsService.s_unReportGoods(goodsReport);
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/judgeGoods/{userId}/{goodsId}")
    public Integer judgeGoodsToUser(@PathVariable("userId") String userid,@PathVariable("goodsId") String goodsId){
        System.out.println("收到的userId为："+userid+" 收到的goodsId为："+goodsId);
        return iForGoodsService.s_judgeGoodsToUser(userid,goodsId);
    }
}
