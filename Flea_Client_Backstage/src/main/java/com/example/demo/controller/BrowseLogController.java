package com.example.demo.controller;

import com.example.demo.pojo.BrowseLog;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.service.BrowseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 浏览记录controller
 * @author 周杨清
 * @date 2021/3/16 21:11
 */
@RestController
@RequestMapping("/browselog")
public class BrowseLogController {

    @Autowired
    BrowseLogService browseLogService;

    /**
     * 存储浏览记录
     * @param userId
     * @param goodsId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/updatebrowseLog/{userId}/{goodsId}")
    public Boolean updateBrowse(
            @PathVariable("userId") String userId
            ,@PathVariable("goodsId") String goodsId){
        System.out.println("userId:"+userId+" goodsId:"+goodsId);
        return browseLogService.s_updateBrowse(goodsId,userId);
    }

    /**
     * 获取历史记录
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUserBrowseLog/{userId}")
    public ArrayList<GoodsIndex> getUserBrowseLog(
            @PathVariable("userId") String userId){
        return browseLogService.s_getUserBrowseLog(userId);
    }

}
