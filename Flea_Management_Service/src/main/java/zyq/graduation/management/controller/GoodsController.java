package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.GoodsMapper;
import zyq.graduation.management.pojo.Goods;
import zyq.graduation.management.pojo.Order;
import zyq.graduation.management.pojo.ReturnClass;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/22 18:42
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsMapper goodsMapper;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsByGoodsId/{goodsId}")
    public Goods getGoods(@PathVariable("goodsId") String goodsId){
        System.out.println(goodsId);
        return goodsMapper.selectGoodsByGoodsId(goodsId);
    }

}
