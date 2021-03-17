package com.example.demo.service.impl;

import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.ForGoodsMapper;
import com.example.demo.mapper.MinePageMapper;
import com.example.demo.mapper.PersonalCenterMapper;
import com.example.demo.pojo.*;
import com.example.demo.service.MinePageService;
import com.example.demo.service.PersonalCenterService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/12 21:35
 */
@Service
public class IPersonalCenterService implements PersonalCenterService {

    @Autowired
    ESDataDao esDataDao;

    @Autowired
    MinePageService minePageService;

    @Autowired
    MinePageMapper minePageMapper;

    @Autowired
    PersonalCenterMapper personalCenterMapper;

    @Autowired
    ForGoodsMapper forGoodsMapper;


    @Override
    public ArrayList<GoodsIndex> s_getGoodsForPersonal(String flea_id,int from, int size, String status) {
        QueryBuilder queryBuilder =null;
        if (status.equals("dead")){
            queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("userId",flea_id))
                    .must(QueryBuilders.matchQuery("status",status));
        }else{
            queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("userId",flea_id))
                    .mustNot(QueryBuilders.matchQuery("status","dead"));
        }
        return esDataDao.selectGoods("goods", queryBuilder,from,size);
    }

    @Override
    public PersonalPageInfo s_getPersonalPagesInfo(String userId,String flea_id) {
        PersonalPageInfo personalPageInfo = new PersonalPageInfo();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("userId",userId))
                .must(QueryBuilders.termQuery("status","dead"));
        personalPageInfo.setDeadNum(esDataDao.selectCount("goods",queryBuilder));
        QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("userId",userId))
                .mustNot(QueryBuilders.termQuery("status","dead"));
        personalPageInfo.setTradingNum(esDataDao.selectCount("goods",queryBuilder2));

        //存收藏数
        personalPageInfo.setCollectNum(minePageMapper.selectCollectNum(userId));

        //关注数
        personalPageInfo.setAttentionNum(minePageMapper.selectAttentionNum(userId));

        //存粉丝数
        personalPageInfo.setFansNum(minePageMapper.selectFansNum(userId));

        //存点赞数
        personalPageInfo.setLikesNum(personalCenterMapper.selectLikesNum(userId));

        //存用户信息
        User user = personalCenterMapper.selectUserInfo(userId);
        personalPageInfo.setUserIcon(user.getUser_icon());
        personalPageInfo.setUserName(user.getNickname());
        personalPageInfo.setUserCredit(user.getCredit());
        personalPageInfo.setUserIntroduce(user.getSelf_introduction());

        //查询关注关系
        personalPageInfo.setFansJudge(forGoodsMapper.selectFans(userId,flea_id));

        return personalPageInfo;
    }

    @Override
    public ArrayList<FansUser> s_getAttention(String userId,String flea_id) {
        ArrayList<String> userIdList= minePageMapper.selectAttentions(userId);
        FansUser fansUser = null;
        ArrayList<FansUser> fansUsers = new ArrayList<>();
        for (String id:userIdList){
            fansUser  = new FansUser();
            fansUser.setESuser(esDataDao.selectUserIndex(id));
            fansUser.setFansJudge(forGoodsMapper.selectFans(fansUser.getESuser().getUserId(), flea_id) == 1);
            fansUsers.add(fansUser);
        }
        return fansUsers;
    }

    @Override
    public ArrayList<FansUser> s_getFans(String userId, String flea_id) {
        ArrayList<String> userIdList= minePageMapper.selectFans(userId);
        FansUser fansUser = null;
        ArrayList<FansUser> fansUsers = new ArrayList<>();
        for (String id:userIdList){
            fansUser  = new FansUser();
            fansUser.setESuser(esDataDao.selectUserIndex(id));
            fansUser.setFansJudge(forGoodsMapper.selectFans(fansUser.getESuser().getUserId(), flea_id) == 1);
            fansUsers.add(fansUser);
        }
        return fansUsers;
    }

    @Override
    public ArrayList<LikesShow> s_getLikes(String userId) {
        ArrayList<Likes> likesArrayList = personalCenterMapper.selectLikes(userId);
        ArrayList<LikesShow> likesShowArrayList = new ArrayList<>();
        GoodsIndex goodsIndex=null;
        ESUser esUser =null;
        LikesShow likesShow =null;
        for (Likes likes:likesArrayList){
            likesShow =new LikesShow();
            /*查询点赞人的username和icon*/
            goodsIndex = esDataDao.selectGoodsIndex(likes.getProduct_id());
            likesShow.setGoodsIcon(goodsIndex.getGoodsPhoto());
            likesShow.setGoodsId(goodsIndex.getGoodsId());
            /*查询商品的icon*/
            esUser = esDataDao.selectUserIndex(likes.getConsumer_id());
            likesShow.setUserIcon(esUser.getUserIcon());
            likesShow.setUserName(esUser.getUserName());
            likesShow.setUserId(esUser.getUserId());

            likesShow.setLikeDate(likes.getLike_date());
            likesShowArrayList.add(likesShow);
        }
        return likesShowArrayList;

    }

    @Override
    public User s_getUserInfo(String userId) {
        return minePageMapper.selectUserInfo(userId);
    }

    @Override
    public Boolean s_updateUserInfo(User user) {
        /*更新es中的user信息*/
        esDataDao.updateUserInfo(user);
        return minePageMapper.updateUserInfo(user);
    }
}
