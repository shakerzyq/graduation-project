package com.example.demo.service.impl;

import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.MinePageMapper;
import com.example.demo.pojo.ESUser;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.pojo.MinePageInfo;
import com.example.demo.pojo.User;
import com.example.demo.pojo.putpojo.UserComplain;
import com.example.demo.service.MinePageService;
import com.example.demo.util.SendEmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/3/9 21:23
 */
@Service
public class IMinePageService implements MinePageService {

    @Autowired
    MinePageMapper minePageMapper;

    @Autowired
    ESDataDao esDataDao;

    @Autowired
    SendEmailMessage sendEmailMessage;

    @Override
    public MinePageInfo s_getMinePagesInfo(String userid) {

        MinePageInfo minePageInfo = new MinePageInfo();

        //查询用户信息
        User user = minePageMapper.selectUserInfo(userid);

        minePageInfo.setUserIcon(user.getUser_icon());
        minePageInfo.setUserName(user.getNickname());
        minePageInfo.setUserCredit(user.getCredit());
        minePageInfo.setUserEmail(user.getEmail());

        //存收藏数
        minePageInfo.setCollectNum(minePageMapper.selectCollectNum(userid));

        //关注数
        minePageInfo.setAttentionNum(minePageMapper.selectAttentionNum(userid));

        //存粉丝数
        minePageInfo.setFansNum(minePageMapper.selectFansNum(userid));

        //查询浏览历史记录
        minePageInfo.setHistoryNum(minePageMapper.selectHistoryNum(userid));

        //存放
        return minePageInfo;
    }

    /**
     * 获取商品收藏具体信息
     * @param userId
     * @return
     */
    @Override
    public ArrayList<GoodsIndex> s_getCollectGoods(String pageNum,String userId) {
        ArrayList<String> collectIds = null;
        collectIds=minePageMapper.selectCollects(userId);
        ArrayList<GoodsIndex> goodsIndices = new ArrayList<>();
        GoodsIndex goodsIndex = null;
        ESUser esUser = null;
        for (String id:collectIds){
            goodsIndex = esDataDao.selectGoodsIndex(id);
            esUser = esDataDao.selectUserIndex(goodsIndex.getUserId());
            goodsIndex.setUserCredit(esUser.getUserCredit());
            goodsIndex.setUserIcon(esUser.getUserIcon());
            goodsIndex.setUserName(esUser.getUserName());
            goodsIndices.add(goodsIndex);
        }
        return goodsIndices;
    }

    /**
     * 查询关注的用户信息
     * @param userId
     * @return
     */
    @Override
    public ArrayList<ESUser> s_getAttention(String userId) {
        ArrayList<String> userIdList= minePageMapper.selectAttentions(userId);
        ESUser esUser = null;
        ArrayList<ESUser> esUsers = new ArrayList<>();
        for (String id:userIdList){
            esUser = esDataDao.selectUserIndex(id);
            esUsers.add(esUser);
        }
        return esUsers;
    }

    /**
     * 获取粉丝信息
     * @param userId
     * @return
     */
    @Override
    public ArrayList<ESUser> s_getFans(String userId) {
        ArrayList<String> userIdList= minePageMapper.selectFans(userId);
        ESUser esUser = null;
        ArrayList<ESUser> esUsers = new ArrayList<>();
        for (String id:userIdList){
            esUser = esDataDao.selectUserIndex(id);
            esUsers.add(esUser);
        }
        return esUsers;
    }

    /**
     * 获取用户发布的商品信息
     * @param userId
     * @return
     */
    @Override
    public ArrayList<GoodsIndex> s_getMinePublish(String pageNum, String userId) {
        Integer from=((Integer.parseInt(pageNum)-1)*8);
        System.out.println("from为"+from);
        ArrayList<GoodsIndex> arrayList = esDataDao.selectGoodsById(from,8,userId);
        System.out.println("收取到的结果为"+arrayList);
        return arrayList;
    }

    /**
     * 发布举报单
     * @param userComplain
     * @return
     */
    @Override
    public Boolean s_putUserComplain(UserComplain userComplain) {
        sendEmailMessage.sendMessage(minePageMapper.selectUserEmail(userComplain.getComplained_userid()),userComplain.getEmail());
        String address =minePageMapper.selectAddress(userComplain.getComplain_userid());
        userComplain.setAddress(address);
        return minePageMapper.insertComplain(userComplain);
    }

    @Override
    public ArrayList<UserComplain> s_getUserComplains(String userId, String orderStatus) {
        if (orderStatus.equals("todo")){
            ArrayList<UserComplain> userComplains = minePageMapper.selectUserComplain(userId, "2");
            for (UserComplain userComplain:userComplains){
                if (userId.equals(userComplain.getComplain_userid())){
                    userComplain.setEmail(minePageMapper.selectUserEmail(userComplain.getComplained_userid()));
                }else{
                    userComplain.setEmail(minePageMapper.selectUserEmail(userComplain.getComplain_userid()));
                }
            }
            return userComplains;
        }else {
            return minePageMapper.selectUserComplainFinished(userId,"2");
        }

    }

    /**
     * 获取举报单详细信息
     * @param complain_id
     * @return
     */
    @Override
    public Object s_getComplainDetail(String complain_id,String status) {
        if (status.equals("2")){
            return minePageMapper.selectDetailComplain(complain_id,status);
        }else{
            return minePageMapper.selectHistoryDetailComplain(complain_id,status);
        }
    }

    /**
     * 删除举报单
     * @param complain_id
     * @return
     */
    @Override
    public Boolean s_deleteComplain(String complain_id) {

        return minePageMapper.deleteComplain(complain_id);
    }
}
