package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.AdminGoodsMapper;
import zyq.graduation.management.pojo.Goods;

/**
 * @author 周杨清
 * @date 2021/3/22 18:42
 */
@RestController
@RequestMapping("/goods")
public class AdminGoodsController {

    @Autowired
    AdminGoodsMapper adminGoodsMapper;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsByGoodsId/{goodsId}")
    public Goods getGoods(@PathVariable("goodsId") String goodsId){
        System.out.println(goodsId);
        return adminGoodsMapper.selectGoodsByGoodsId(goodsId);
    }

}
