package com.example.demo.service;

import com.example.demo.pojo.*;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/12 21:34
 */
public interface PersonalCenterService {
    ArrayList<GoodsIndex> s_getGoodsForPersonal(String flea_id,int from, int size, String status);

    PersonalPageInfo s_getPersonalPagesInfo(String userId,String flea_id);

    ArrayList<FansUser> s_getAttention(String userId, String flea_id);

    ArrayList<FansUser> s_getFans(String userId, String flea_id);

    ArrayList<LikesShow> s_getLikes(String userId);

    User s_getUserInfo(String userId);

    Boolean s_updateUserInfo(User user);
}
