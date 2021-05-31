package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.AdminGoodsMapper;
import zyq.graduation.management.pojo.Goods;
import zyq.graduation.management.pojo.GoodsReport;
import zyq.graduation.management.pojo.ReturnClass;
import zyq.graduation.management.service.AdminGoodsService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/22 18:42
 */
@RestController
@RequestMapping("/goods")
public class AdminGoodsController {

    @Autowired
    AdminGoodsMapper adminGoodsMapper;

    @Autowired
    AdminGoodsService adminGoodsService;

    /**
     * 获取商品详细信息
     * @param goodsId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsByGoodsId/{goodsId}")
    public Goods getGoods(@PathVariable("goodsId") String goodsId){
        System.out.println(goodsId);
        return adminGoodsMapper.selectGoodsByGoodsId(goodsId);
    }

    /**
     * 获取管理员对应的address的商品举报单
     * @param page
     * @param limit
     * @param adminAccount
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsReports/{adminAccount}")
    public ReturnClass<ArrayList<GoodsReport>> getGoodsReports(@PathParam("page") Integer page
            ,@PathParam("limit") Integer limit
            , @PathVariable("adminAccount") String adminAccount){
        System.out.println("管理员账号为："+adminAccount);
        ArrayList<GoodsReport> goodsList =  adminGoodsService.getGoodsReports(page-1,limit,adminAccount);
        return new ReturnClass<ArrayList<GoodsReport>>(0,"",goodsList.size(),goodsList);
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getAccomplishGoodsReports/{adminAccount}")
    public ReturnClass<ArrayList<GoodsReport>> getAccomplishGoodsReports(
            @PathParam("page") Integer page
            , @PathParam("limit") Integer limit
            ,@PathParam("userId") String userId
            , @PathParam("status") String status
            ,@PathVariable("adminAccount") String adminAccount){
        System.out.println("hello world!");
        ArrayList<GoodsReport> goodsList =  adminGoodsService.getGoodsAccomplishReports(page-1,limit,userId,status,adminAccount);
        return new ReturnClass<ArrayList<GoodsReport>>(0,"",goodsList.size(),goodsList);
    }


    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/disposeReport/{reportId}/{content}/{type}/{complain_userid}/{complained_userid}/{goods_id}/{adminAccount}")
    public Boolean  disposeGoodsReport(
            @PathVariable("reportId") String reportId
            ,@PathVariable("type") String type
            ,@PathVariable("content") String content
            ,@PathVariable("complain_userid") String complain_userid
            ,@PathVariable("complained_userid") String complained_userid
            ,@PathVariable("goods_id") String goods_id
            ,@PathVariable("adminAccount") String adminAccount
    ){
        System.out.println("++++++++++++++++type:"+type+"reportId:"+reportId+"content:"+content+" "+complain_userid+" "+complained_userid+" "+goods_id+" "+adminAccount);
        return adminGoodsService.disposeGoodsReport(type,reportId,content,complain_userid,complained_userid,goods_id,adminAccount);
    }

}
