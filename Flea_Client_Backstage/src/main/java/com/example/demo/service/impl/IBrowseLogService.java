package com.example.demo.service.impl;

import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.BrowseLogMapper;
import com.example.demo.pojo.BrowseLog;
import com.example.demo.pojo.ESUser;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.service.BrowseLogService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/3/16 21:18
 */
@Service
public class IBrowseLogService implements BrowseLogService {

    @Autowired
    ESDataDao esDataDao;

    @Autowired
    BrowseLogMapper browseLogMapper;

    @Override
    public Boolean s_updateBrowse(String goodsId, String userId) {
        if (browseLogMapper.selectBrowseNum(goodsId,userId)==0){

            return browseLogMapper.insertBrowseLog(UUID.randomUUID().toString(),goodsId,userId,System.currentTimeMillis());
//            return browseLogMapper.updateBrowseNum(goodsId,userId);
        }else {
            return browseLogMapper.updateBrowseDate(System.currentTimeMillis(),goodsId,userId);
        }
    }


    @Override
    public ArrayList<GoodsIndex> s_getUserBrowseLog(String userId) {
        ArrayList<String> browseGoodsId = browseLogMapper.selectBrowseLog(userId);
        ArrayList<GoodsIndex> list = new ArrayList<>();
        GoodsIndex goodsIndex = null;
        ESUser esUser = null;
        for (String goodsid:browseGoodsId){
            goodsIndex = esDataDao.selectGoodsIndex(goodsid);
            esUser = esDataDao.selectUserIndex(goodsIndex.getUserId());
            goodsIndex.setUserCredit(esUser.getUserCredit());
            goodsIndex.setUserIcon(esUser.getUserIcon());
            goodsIndex.setUserName(esUser.getUserName());
            list.add(goodsIndex);
        }
        /* 查询ES中的信息*/
        return list;
    }
}
