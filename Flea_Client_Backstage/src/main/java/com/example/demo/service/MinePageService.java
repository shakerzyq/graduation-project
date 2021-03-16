package com.example.demo.service;

import com.example.demo.pojo.ESUser;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.pojo.MinePageInfo;
import com.example.demo.pojo.putpojo.UserComplain;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/9 21:23
 */
public interface MinePageService {
    MinePageInfo s_getMinePagesInfo(String userid);

    ArrayList<GoodsIndex>  s_getCollectGoods(String pageNum,String userId);

    ArrayList<ESUser> s_getAttention(String userId);

    ArrayList<ESUser> s_getFans(String userId);

    ArrayList<GoodsIndex> s_getMinePublish(String userId,String PageNum);

    Boolean s_putUserComplain(UserComplain userComplain);

    ArrayList<UserComplain> s_getUserComplains(String userId, String orderStatus);

    /**
     * 获取举报单详细信息
     * @param complain_id
     * @return
     */
    Object s_getComplainDetail(String complain_id,String status);


    Boolean s_deleteComplain(String complain_id);
}
