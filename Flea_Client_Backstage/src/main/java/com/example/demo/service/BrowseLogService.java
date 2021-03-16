package com.example.demo.service;

import com.example.demo.pojo.BrowseLog;
import com.example.demo.pojo.GoodsIndex;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/16 21:16
 */
public interface BrowseLogService {

    Boolean s_updateBrowse(String goodsId, String userId);

    ArrayList<GoodsIndex> s_getUserBrowseLog(String userId);
}
