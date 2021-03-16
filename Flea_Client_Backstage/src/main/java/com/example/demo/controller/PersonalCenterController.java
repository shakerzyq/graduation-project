package com.example.demo.controller;

import com.example.demo.mapper.PersonalCenterMapper;
import com.example.demo.pojo.*;
import com.example.demo.service.PersonalCenterService;
import com.example.demo.service.impl.IForGoodsService;
import com.example.demo.service.impl.IPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/12 21:33
 */
@RestController
@RequestMapping("/personal")
public class PersonalCenterController {

    @Autowired
    PersonalCenterService personalCenterService;

    @Autowired
    PersonalCenterMapper personalCenterMapper;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsInfo/{pagenum}/{flea_id}/{status}")
    public ArrayList<GoodsIndex> getGoodsForIndex(@PathVariable("pagenum")String pagenum,
                                                  @PathVariable("flea_id") String flea_id,
                                                  @PathVariable("status") String status){
        System.out.println("获取跳蚤id为："+flea_id+" pagenum为："+pagenum+"status为："+status);
        //获取所有商品
//        ArrayList<GoodsIndex> result = iForGoodsService.getGoods(((Integer.parseInt(pagenum)-1)*8),8);
        ArrayList<GoodsIndex> result = personalCenterService.s_getGoodsForPersonal(flea_id,((Integer.parseInt(pagenum)-1)*8),8,status);
        System.out.println(result);
        return result;
    }

    /**
     * 获取用户主页需要显示的信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/userinfo/{userId}/{flea_id}")
    public PersonalPageInfo getPersonalPagesInfo(@PathVariable("userId") String userId,@PathVariable("flea_id") String flea_id){
        System.out.println("u收到的userid为："+userId);
        return personalCenterService.s_getPersonalPagesInfo(userId,flea_id);
    }
    /**
     * 查看用户的关注
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/attentionInfo/{userId}/{flea_id}")
    public ArrayList<FansUser> getAttention(@PathVariable("userId") String userId, @PathVariable("flea_id") String flea_id){
        System.out.println("获取到的id为"+userId+" flea_id为："+flea_id);
        return personalCenterService.s_getAttention(userId,flea_id);
    }

    /**
     * 查看用户的粉丝
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/fansInfo/{userId}/{flea_id}")
    public ArrayList<FansUser> getFans(@PathVariable("userId") String userId, @PathVariable("flea_id") String flea_id){
        System.out.println("获取到的id为"+userId+" flea_id为："+flea_id);
        return personalCenterService.s_getFans(userId,flea_id);
    }

    /**
     * 获取用户的所有被点赞信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/likesInfo/{userId}")
    public ArrayList<LikesShow> getLikes(@PathVariable("userId") String userId){
        return personalCenterService.s_getLikes(userId);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUserInfo/{userId}")
    public User getUserInfo(@PathVariable("userId") String userId){
        return personalCenterService.s_getUserInfo(userId);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping("/updateUserInfo")
    public Boolean updateUserInfo(@RequestBody User user){
        System.out.println(user);
        return personalCenterService.s_updateUserInfo(user);
    }

    /**
     * 更新用户邮箱
     * @param userId
     * @param email
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping("/updateUserEmial/{userId}/{email}")
    public Boolean updateuserEmail(
            @PathVariable("userId") String userId
            ,@PathVariable("email") String email){
        return personalCenterMapper.updateUserEmail(userId,email);
    }

}
