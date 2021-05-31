package com.example.demo.controller;

import com.example.demo.pojo.ESGoods;
import com.example.demo.pojo.ESUser;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.pojo.MinePageInfo;
import com.example.demo.pojo.putpojo.UserComplain;
import com.example.demo.service.MinePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 *
 * 我的界面
 * 处理对应http请求业务逻辑
 * @author 周杨清
 * @date 2021/3/9 21:13
 */
@RestController
@RequestMapping("/mine")
public class MinePageController {

    @Autowired
    MinePageService minePageService;

    /**
     * 获取mine主页需要显示的信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/mineinfo/{userId}")
    public MinePageInfo getMinePagesInfo(@PathVariable("userId") String userId){
        return minePageService.s_getMinePagesInfo(userId);
    }

    /**
     * 获取收藏商品
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/collectInfo/{pageNum}/{userId}")
    public ArrayList<GoodsIndex> getCollectGoods(@PathVariable("pageNum") String pageNum,@PathVariable("userId") String userId){
        System.out.println("userId为："+userId);
        return minePageService.s_getCollectGoods(pageNum,userId);
    }

    /**
     * 查看关注的用户
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/attentionInfo/{userId}")
    public ArrayList<ESUser> getAttention(@PathVariable("userId") String userId){
        System.out.println("获取到的id为"+userId);
        return minePageService.s_getAttention(userId);
    }

    /**
     * 查看粉丝信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/fansInfo/{userId}")
    public ArrayList<ESUser> getFans(@PathVariable("userId") String userId){
        System.out.println("获取到的id为"+userId);
        return minePageService.s_getFans(userId);
    }

    /**
     * 查询我的发布内容
     * @param pageNum
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/publishInfo/{pagenum}/{userId}")
    public ArrayList<GoodsIndex> getMinePublish(@PathVariable("pagenum") String pageNum,@PathVariable("userId") String userId){
        System.out.println("获取到的id为"+userId+"页面号为："+pageNum);
        return minePageService.s_getMinePublish(pageNum,userId);
    }

    /**
     * 发布举报单
     * @param userComplain
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping("/putUserComplain")
    public Boolean putOrderComplain(@RequestBody UserComplain userComplain){
        System.out.println(userComplain);
        return minePageService.s_putUserComplain(userComplain);
    }

    /**
     * 获取举报单信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUserComplain/{user_id}/{orderStatus}")
    public ArrayList<UserComplain> getUserComplains(
            @PathVariable("user_id") String userId
            ,@PathVariable("orderStatus") String orderStatus){
        System.out.println("orderStatus为："+orderStatus);
        return minePageService.s_getUserComplains(userId,orderStatus);
    }

    /**
     * 获取举报单详细信息
     * @param complain_id
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getComplainDetail/{complain_id}/{status}")
    public Object getComplainDetail(
            @PathVariable("complain_id") String complain_id
            ,@PathVariable("status") String status){
        System.out.println("complain_id"+complain_id);
        return minePageService.s_getComplainDetail(complain_id,status);
    }

    /**
     * 删除举报单
     * @param complain_id
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @DeleteMapping("/deleteComplain/{complain_id}")
    public Boolean deleteComplain(@PathVariable("complain_id") String complain_id){
        System.out.println("complain_id"+complain_id);
        return minePageService.s_deleteComplain(complain_id);
    }
}
