package com.example.demo.service.impl;

import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.BrowseLogMapper;
import com.example.demo.mapper.ForGoodsMapper;
import com.example.demo.pojo.*;
import com.example.demo.service.ForGoodsService;
import io.swagger.models.auth.In;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author 周杨清
 * @date 2021/2/21 11:54
 */
@Service

public class IForGoodsService implements ForGoodsService {

    @Autowired
    ForGoodsMapper forGoodsMapper;

    @Autowired
    ESDataDao esDataDao;

    @Autowired
    BrowseLogMapper browseLogMapper;

    @Override
    public Boolean putGoods(Goods goods) throws IOException {

        goods.setProduct_id(UUID.randomUUID().toString());
        System.out.println("product_id为"+goods.getProduct_id().replace("-", ""));
        //将Goods转化为product

       /* Products products = new Products();
        products.setProduct_id(goods.getProduct_id());
        products.setMerchant_id(goods.getMerchant_id());
        products.setProduct_des(goods.getProduct_des());
        products.setNow_price(goods.getNow_price());
        products.setOld_price(goods.getOld_price());
        products.setQuality(goods.getQuality());
        products.setClass_choice(goods.getClass_choice());*/


        /*String urls = "";
        for (String url:goods.getPhotos()){
            if (!urls.equals("")){
                urls=urls+","+url;
            }else {
                urls=url;
            }
        }*/
        //products.setPhotos(urls);
        goods.setAdd_date(System.currentTimeMillis());
        Boolean result = forGoodsMapper.InsertProduct(goods);

        /**
         * 将数据存储到ES中
         */

        ESGoods esGoods = new ESGoods();
        //user相关
        esGoods.setUserId(goods.getMerchant_id());
        //通过merchant_ID查询用户信息
//        User user = new User();
//        user = forGoodsMapper.SelectByUserId(goods.getMerchant_id());
//        goodsIndex.setUserName(user.getNickname());
//        goodsIndex.setUserIcon(user.getUser_icon());
//        goodsIndex.setUserCredit(user.getCredit());

        //商品相关
        esGoods.setGoodsTitle(goods.getProduct_title());
        esGoods.setGoodsDes(goods.getProduct_des());
        esGoods.setGoodsType(goods.getAtype()+goods.getBtype()+goods.getCtype()+"");
        if (goods.getPhotos().contains(",")){
            esGoods.setGoodsPhoto(goods.getPhotos().split(",")[0]);
        }else{
            esGoods.setGoodsPhoto(goods.getPhotos());
        }
        esGoods.setGoodsPrice(goods.getNow_price());
        esGoods.setGoodsId(goods.getProduct_id());
        esGoods.setAddPlace(goods.getAdd_place());
        //添加商品操作默认都为零
        esGoods.setViewsNum(0);
        esGoods.setGoodsWanter(0);
        esGoods.setLikesNum(0);
        esGoods.setStatus("living");
        esDataDao.insertGoods(esGoods);
        return result;
    }

    /**
     * 查询--用于更新商品
     * @param goodsId
     * @return
     */
    @Override
    public Goods s_getGoodsToUpdate(String goodsId) {
        return forGoodsMapper.selectGoodsByGoodsId(goodsId);
    }

    @Override
    public ArrayList<GoodsIndex> s_getGoodsForIndex(int from, int size,String status,String status2,String addPlace) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .mustNot(QueryBuilders.matchQuery("status",status))
                .mustNot(QueryBuilders.matchQuery("status",status2))
                .must(QueryBuilders.matchQuery("addPlace",addPlace));
        return esDataDao.selectGoods("goods", queryBuilder,from,size);
    }

    /**
     * 通过输入的内容来检索商品信息
     * @param from
     * @param end
     * @param value
     * @return
     */
    @Override
    public ArrayList<GoodsIndex> s_searchGoodsList(int from, int end, String value) {

        return null;
    }

    /**
     * 更新商品信息
     * @param goods
     *
     * @return
     */
    @Override
    public Boolean s_updateProduct(Goods goods) {
        //更新Mysql
        Boolean result= forGoodsMapper.updateGoodsInfo(goods);

        //更新ES数据
        esDataDao.updateGoodsInfo(goods);
        return result;
    }

    @Override
    public Boolean s_deleteProduct(String goodsId) {
        //删除Mysql--product,
        Boolean result = forGoodsMapper.deleteGoodsInfo(goodsId);
        //comment,
        forGoodsMapper.deleteCommentByGoodsId(goodsId);
        // commet_1,
        forGoodsMapper.deleteComment_1ByGoodsId(goodsId);
        // comment_likes,
        forGoodsMapper.deleteComment_likeByGoodsId(goodsId);
        // comment_relation数据
        //

        //删除ES数据
        esDataDao.deleteGoodsInfo(goodsId);

        //删除所有有关的浏览记录
        browseLogMapper.deleteBrowseLog(goodsId);
        return result;
    }

    /**
     * 判断用户是否对该商品下单
     * @param userid
     * @param goodsId
     * @return
     */
    @Override
    public Integer s_judgeGoodsToUser(String userid, String goodsId) {
        return forGoodsMapper.selectOrders(userid,goodsId);
    }

    /**
     *获取商品信息
     * @param start
     * @param end
     * @return
     */
    @Override
    public ArrayList<GoodsIndex> getGoods(Integer start,Integer end) {
        System.out.println("进入了");
        ArrayList<Goods> result = forGoodsMapper.selectGoods(start,end);
        ArrayList<GoodsIndex> goodslist = new ArrayList<>();
        HashMap<String,User> hashMap = new HashMap<>();
        User user=null;
        for (Goods products:result){
            if (hashMap.containsKey(products.getMerchant_id())){
                user =  hashMap.get(products.getMerchant_id());
            }else {
                //通过merchant_ID查询用户信息
                user = forGoodsMapper.SelectByUserId(products.getMerchant_id());
                hashMap.put(products.getMerchant_id(),user);
            }

            GoodsIndex goodsIndex = new GoodsIndex();
            //用户相关
            goodsIndex.setUserId(products.getMerchant_id());
            goodsIndex.setUserName(user.getNickname());
            goodsIndex.setUserIcon(user.getUser_icon());
            goodsIndex.setUserCredit(user.getCredit());

            //商品相关
            goodsIndex.setGoodsId(products.getProduct_id());
            goodsIndex.setGoodsDes(products.getProduct_des());
            goodsIndex.setGoodsPrice(products.getNow_price());
            goodsIndex.setGoodsPhoto(products.getPhotos().split(",")[0]);

            //收藏数
            goodsIndex.setGoodsWanter(100);
            goodslist.add(goodsIndex);
        }

        System.out.println("goodList为"+goodslist);


        /*System.out.println("service层的result为"+result);

        for (Products products:result){

            String[] arr = products.getPhotos().split(",");
            System.out.println("每次的arr为"+arr);
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arr));
            Goods goods = new Goods();
            goods.setClass_choice(products.getClass_choice());
            goods.setMerchant_id(products.getMerchant_id());
            goods.setProduct_id(products.getProduct_id());
            goods.setNow_price(products.getNow_price());
            goods.setOld_price(products.getOld_price());
            goods.setQuality(goods.getQuality());
            goods.setProduct_des(products.getProduct_des());
            goods.setPhotos(arrayList);
            goodslist.add(goods);
        }*/



        return goodslist;
    }

    /**
     * 获取商品用户的详细信息
     * @param merchantId
     * @param fleaId
     * @param goodsId
     * @return
     */
    @Override
    public GoodsAndUser s_GoodsDetailInfo(String merchantId,String fleaId,String goodsId) {

        //增加一次浏览量
        forGoodsMapper.updateGoodsViews(goodsId);

        //查询关注关系
        Integer fansJudge = forGoodsMapper.selectFans(merchantId,fleaId);

        /*if (fansJudge==null){

        }*/
        System.out.println("fansjudge为"+fansJudge);

        //查询商品信息
        Goods goods = forGoodsMapper.slectGoodsByGoodsId(goodsId);
        //查询用户信息
        User user = forGoodsMapper.SelectByUserId(merchantId);

        //查询观察者信息
        User user2 = forGoodsMapper.SelectByUserId(fleaId);

        //查询商品点赞数
        //查询商品收藏数
        LikeAndCollect likeAndCollect = forGoodsMapper.selectLikeByGoodsId(goodsId);

        System.out.println("商品的喜欢数和收藏数为："+likeAndCollect);



        //使用多表查询

        //获取评论数量

        GoodsAndUser goodsAndUser = new GoodsAndUser();
        goodsAndUser.setUserIcon(user.getUser_icon());
        goodsAndUser.setUserName(user.getNickname());
        goodsAndUser.setUserId(merchantId);


        goodsAndUser.setObserverIcon(user2.getUser_icon());
        goodsAndUser.setObserverName(user2.getNickname());

        goodsAndUser.setFansJudge(fansJudge);

        goodsAndUser.setGoodsTitle(goods.getProduct_title());
        goodsAndUser.setGoodsClass(goods.getClass_choice());
        goodsAndUser.setGoodsQuality(goods.getQuality());
        goodsAndUser.setBrand(goods.getBrand());
        goodsAndUser.setGoodsOldPrice(goods.getOld_price());
        goodsAndUser.setGoodsDes(goods.getProduct_des());
        goodsAndUser.setGoodsNowPrice(goods.getNow_price());
        goodsAndUser.setPhotos(goods.getPhotos());
        goodsAndUser.setGoodsViews(goods.getViews());
        goodsAndUser.setCommentNum(forGoodsMapper.selectCommentNum(goodsId));//插入评论数量
        goodsAndUser.setAtype(goods.getAtype());
        goodsAndUser.setBtype(goods.getBtype());
        goodsAndUser.setCtype(goods.getCtype());
        goodsAndUser.setAddTime(goods.getAdd_date());
        goodsAndUser.setStatus(esDataDao.selectGoodsIndex(goodsId).getStatus());



        //查询用户对商品的点赞状态

        if(forGoodsMapper.selectGoodsLikeJudge(fleaId,goodsId)==0){
            goodsAndUser.setGoodsLikeJudge(false);
        }else{
            goodsAndUser.setGoodsLikeJudge(true);
        }

        if (forGoodsMapper.selectGoodsCollectJudge(fleaId,goodsId)==0){
            goodsAndUser.setGoodsCollectJudge(false);
        }else{
            goodsAndUser.setGoodsCollectJudge(true);
        }

        //查询对商品的用户举报状态
        if (forGoodsMapper.selectGoodsReportJudge(fleaId,goodsId)==0){
            goodsAndUser.setGoodsReportJudge(false);
        }else{
            goodsAndUser.setGoodsReportJudge(true);
        }
        if (goodsAndUser.getGoodsReportJudge()){//举报状态为true才查询举报内容
            String str = forGoodsMapper.selectReportContent(fleaId,goodsId);
            System.out.println("查询出来的violate_content为："+str);
            String[] arr = str.split(",");

            goodsAndUser.setGoodsReportContent(new ArrayList<>(Arrays.asList(arr)));
        }

        //查询用户对商品的收藏状态

        goodsAndUser.setGoodsCollect(likeAndCollect.getCollectNum());
        goodsAndUser.setGoodsLike(likeAndCollect.getLikeNum());

        System.out.println("浏览量为"+goodsAndUser.getGoodsViews());
        return goodsAndUser;
    }

    /**
     * 处理关注的业务逻辑
     * @param feal_id
     * @param merchan_id
     * @param type
     * @return
     */
    @Override
    public Integer s_fansAction(String feal_id, String merchan_id, String type) {
        if(type.equals("fans")){

            forGoodsMapper.insertFans(feal_id,merchan_id);
            return 1;
        }else{
            forGoodsMapper.deleteFans(feal_id,merchan_id);
            return 0;
        }
    }

    /**
     * 点赞的事件，同步更新ES中的数据
     * @param flea_id
     * @param goods_id
     * @param merchant_id
     * @param type
     * @return
     */
    @Override
    public Boolean s_goodsLikeAction(String flea_id, String goods_id,String merchant_id,String type) {
        if (type.equals("true")){//取消点赞
            esDataDao.updateCollectDown("goods",goods_id,true,"like");
            return forGoodsMapper.deleteGoodsLike(flea_id,goods_id);
        }else{//点赞
            esDataDao.updateCollectDown("goods",goods_id,false,"like");
            return forGoodsMapper.insertGoodsLike(flea_id,goods_id,merchant_id,System.currentTimeMillis());
        }

    }

    /**
     * 收藏的事件，同步更新ES中的数据
     * @param flea_id
     * @param goods_id
     * @param merchant_id
     * @param type
     * @return
     */
    @Override
    public Boolean s_goodsCollectAction(String flea_id, String goods_id,String merchant_id,String type) {
        if (type.equals("true")){//取消收藏
            esDataDao.updateCollectDown("goods",goods_id,true,"collect");
            return forGoodsMapper.deleteGoodsCollect(flea_id,goods_id);
        }else{//点赞
            esDataDao.updateCollectDown("goods",goods_id,false,"collect");
            return forGoodsMapper.insertGoodsCollect(flea_id,goods_id,merchant_id);
        }
    }


    /**
     * 商品举报
     * 根据type判断是更新举报还是添加举报
     * @param goodsReport
     * @param type
     * @return
     */
    @Override
    public Boolean s_reportGoods(GoodsReport goodsReport,String type) {
        if (type.equals("false")){//添加举报
            return forGoodsMapper.insertGoodsReport(
                    UUID.randomUUID().toString(),
                    goodsReport.getInformer_id(),
                    goodsReport.getViolate_goods_id(),
                    goodsReport.getViolate_user_id(),
                    goodsReport.getViolate_content());
        }else{//更新举报
            return forGoodsMapper.updateGoodsReport(
                    goodsReport.getInformer_id(),
                    goodsReport.getViolate_goods_id(),
                    goodsReport.getViolate_content());
        }
    }

    /**
     * 取消商品举报
     * @param goodsReport
     * @return
     */
    @Override
    public Boolean s_unReportGoods(GoodsReport goodsReport) {
        return forGoodsMapper.deleteGoodsReport(goodsReport.getInformer_id(),goodsReport.getViolate_goods_id());

    }
}
