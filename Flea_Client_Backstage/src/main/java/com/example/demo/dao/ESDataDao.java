package com.example.demo.dao;

import com.example.demo.config.ElasticSearchConfig;
import com.example.demo.pojo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 周杨清
 * @date 2021/3/4 20:38
 */
@Component
public class ESDataDao {

    @Autowired
    private RestHighLevelClient client;

    static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 查询types
     * @param index
     * @param queryBuilder
     * @param needType
     * @return
     * @throws IOException
     */
    public ArrayList<ESGoodsTypes> getTypes(String index, QueryBuilder queryBuilder,String needType) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(50);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        ObjectMapper objectMapper = new ObjectMapper();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
        ArrayList<ESGoodsTypes> arrayList = new ArrayList<>();
        for(SearchHit hit:searchHits){
            ESGoodsTypes esGoodsTypes = new ESGoodsTypes();
            String json = hit.getSourceAsString();
            //System.out.println(json);
            Map map = objectMapper.readValue(json,Map.class);
            esGoodsTypes.setType(map.get(needType).toString());
            arrayList.add(esGoodsTypes);
        }

        return arrayList;
    }

    /**
     * 插入一个新的商品
     * @param esGoods
     * @throws IOException
     */
    public void insertGoods(ESGoods esGoods) throws IOException {
        IndexRequest indexRequest = new IndexRequest("goods");
        indexRequest.id(esGoods.getGoodsId());
        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String,Object> map = new HashMap();
//        goodsIndex.setUserId(map.get("userId").toString());
//        goodsIndex.setGoodsTitle(map.get("goodsTitle").toString());
//        goodsIndex.setGoodsDes(map.get("goodsDes").toString());
//        goodsIndex.setGoodsType(map.get("goodsType").toString());
//        goodsIndex.setGoodsPhoto(map.get("goodsPhoto").toString());
//        goodsIndex.setGoodsPrice((Double)map.get("goodsPrice"));
//        goodsIndex.setGoodsWanter((Integer) map.get("goodsWanter"));
//        goodsIndex.setGoodsId(map.get("goodsId").toString());
//        goodsIndex.setViewsNum((Integer) map.get("viewsNum"));
//        goodsIndex.setLikesNum((Integer) map.get("likesNum"));
//        map.put("userId",goodsIndex.getUserId());
//        map.put("goodsTitle",goodsIndex.getGoodsTitle());
//        map.put("goodsDes",goodsIndex.getGoodsDes());
//        map.put("goodsType");

        String json = objectMapper.writeValueAsString(esGoods);
        indexRequest.source(json, XContentType.JSON);

        IndexResponse index = client.index(indexRequest,ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println("响应数据"+index);
    }

    /**
     * 工具方法，封装GoodIndex包装代码
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    private GoodsIndex toGoodsIndex(String json) throws JsonProcessingException {
        Map map = objectMapper.readValue(json,Map.class);
        GoodsIndex goodsIndex = new GoodsIndex();
        goodsIndex.setUserId(map.get("userId").toString());
        goodsIndex.setGoodsTitle(map.get("goodsTitle").toString());
        goodsIndex.setGoodsDes(map.get("goodsDes").toString());
        goodsIndex.setGoodsType(map.get("goodsType").toString());
        goodsIndex.setGoodsPhoto(map.get("goodsPhoto").toString());
        goodsIndex.setGoodsPrice((Double)map.get("goodsPrice"));
        goodsIndex.setGoodsWanter((Integer) map.get("goodsWanter"));
        goodsIndex.setGoodsId(map.get("goodsId").toString());
        goodsIndex.setViewsNum((Integer) map.get("viewsNum"));
        goodsIndex.setLikesNum((Integer) map.get("likesNum"));
        goodsIndex.setAddPlace(map.get("addPlace").toString());

        ESUser esUser = selectUserIndex(goodsIndex.getUserId());
        goodsIndex.setUserName(esUser.getUserName());
        goodsIndex.setUserIcon(esUser.getUserIcon());
        goodsIndex.setUserCredit(esUser.getUserCredit());
        return goodsIndex;
    }

    private GoodsIndex toGoodsIndex(String json,String consumerId) throws JsonProcessingException {
        Map map = objectMapper.readValue(json,Map.class);
        GoodsIndex goodsIndex = new GoodsIndex();
        goodsIndex.setUserId(map.get("userId").toString());
        goodsIndex.setGoodsTitle(map.get("goodsTitle").toString());
        goodsIndex.setGoodsDes(map.get("goodsDes").toString());
        goodsIndex.setGoodsType(map.get("goodsType").toString());
        goodsIndex.setGoodsPhoto(map.get("goodsPhoto").toString());
        goodsIndex.setGoodsPrice((Double)map.get("goodsPrice"));
        goodsIndex.setGoodsWanter((Integer) map.get("goodsWanter"));
        goodsIndex.setGoodsId(map.get("goodsId").toString());
        goodsIndex.setViewsNum((Integer) map.get("viewsNum"));
        goodsIndex.setLikesNum((Integer) map.get("likesNum"));

        ESUser esUser = selectUserIndex(consumerId);
        goodsIndex.setUserName(esUser.getUserName());
        goodsIndex.setUserIcon(esUser.getUserIcon());
        goodsIndex.setUserCredit(esUser.getUserCredit());
        return goodsIndex;
    }

    /**
     * ,"goodsId",order.getGoods_id()
     * 查询物品，用于推荐页面,
     * @param index
     * @param queryBuilder
     * @param from
     * @param size
     * @return
     */
    public ArrayList<GoodsIndex> selectGoods(String index,QueryBuilder queryBuilder,Integer from,Integer size){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        searchRequest.source(sourceBuilder);
        ArrayList<GoodsIndex> goodsIndices = new ArrayList<>();
        try {
            SearchResponse searchResponse = client.search(searchRequest,ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for (SearchHit hit:searchHits){
                String json = hit.getSourceAsString();

                goodsIndices.add(toGoodsIndex(json));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsIndices;
    }

    /**
     * 查询物品，订单查询
     * @param index
     * @param queryBuilder
     * @param from
     * @param size
     * @return
     */
    public GoodsIndex selectGoodsForOrders(String index,QueryBuilder queryBuilder,Integer from,Integer size){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        searchRequest.source(sourceBuilder);
        GoodsIndex goodsIndex = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest,ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            String json = searchHits[0].getSourceAsString();
            goodsIndex = toGoodsIndex(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return goodsIndex;
    }

    public GoodsIndex selectGoodsForOrders(String index,QueryBuilder queryBuilder,Integer from,Integer size,String consumerId){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        searchRequest.source(sourceBuilder);
        GoodsIndex goodsIndex = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest,ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            if (searchHits.length>0){
                String json = searchHits[0].getSourceAsString();
                goodsIndex = toGoodsIndex(json,consumerId);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsIndex;
    }
    /**
     * 更新goods中商品的状态
     * @param index
     * @param id
     * @param status
     */
    public void updateGoodsStatus(String index,String id,String status){

        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        UpdateRequest updateRequest = new UpdateRequest(index,id).doc(map);
        UpdateResponse updateResponse = null;
        try {
            updateResponse =  client.update(updateRequest,ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(updateResponse);
    }

    /**
     * ES加减---更新
     *
     * 可以对喜欢，收藏处理
     *
     * @param index
     * @param id
     * @param judge
     */
    public void updateCollectDown(String index, String id,Boolean judge,String collectOrLike) {

        GoodsIndex goodsIndex = selectGoodsIndex(id);

        Map<String, Object> jsonMap = new HashMap<>();
        if (judge){
            if (collectOrLike.equals("like")){
                jsonMap.put("likesNum", goodsIndex.getLikesNum()-1);
            }else {
                jsonMap.put("goodsWanter", goodsIndex.getGoodsWanter()-1);
            }

        }else {
            if (collectOrLike.equals("like")){
                jsonMap.put("likesNum", goodsIndex.getLikesNum()+1);
            }else {
                jsonMap.put("goodsWanter", goodsIndex.getGoodsWanter()+1);
            }
        }

        UpdateRequest updateRequest = new UpdateRequest(index,id).doc(jsonMap);

        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(updateRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(updateResponse);
    }


    public void upGoodsViews(String index, String id) {

        GoodsIndex goodsIndex = selectGoodsIndex(id);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("viewsNum", goodsIndex.getViewsNum()+1);

        UpdateRequest updateRequest = new UpdateRequest(index,id).doc(jsonMap);

        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(updateRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(updateResponse);
    }

    /**
     * 通过商品id查询
     * 服务于 同类方法updateCollectDown
     * @param id
     * @return
     */
    public GoodsIndex selectGoodsIndex(String id){
        ObjectMapper objectMapper = new ObjectMapper();
        GetRequest getRequest = new GetRequest("goods",id);

        GetResponse getResponse = null;
        GoodsIndex goodsIndex=null;
        try {
            getResponse = client.get(getRequest, ElasticSearchConfig.COMMON_OPTIONS);
            String sourceAsString = getResponse.getSourceAsString();
            goodsIndex =objectMapper.readValue(sourceAsString,GoodsIndex.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsIndex;
    }

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    public ESUser selectUserIndex(String id){
        ObjectMapper objectMapper = new ObjectMapper();
        GetRequest getRequest = new GetRequest("user",id);

        GetResponse getResponse = null;
        ESUser user =null;
        try {
            getResponse = client.get(getRequest, ElasticSearchConfig.COMMON_OPTIONS);
            String sourceAsString = getResponse.getSourceAsString();
            user =objectMapper.readValue(sourceAsString,ESUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 获取检索结果
     * @param index
     * @param queryBuilder
     * @return
     */
    public ArrayList<RetrievalResult> searchTitle(String index,MatchQueryBuilder queryBuilder) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(50);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(true).field("goodsTitle")
                .preTags("<strong style='color:#FFAA00'>").postTags("</strong>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        ArrayList<RetrievalResult> arrayList =null;
        try {
            searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            ObjectMapper objectMapper = new ObjectMapper();
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
            arrayList = new ArrayList<>();
            for(SearchHit hit:searchHits){
                RetrievalResult retrievalResult = new RetrievalResult();
                String json = hit.getSourceAsString();
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                String goodsTitle  = (String)hit.getSourceAsMap().get("goodsTitle");
                retrievalResult.setResult(goodsTitle);
                if (highlightFields!=null){
                    HighlightField nameField = highlightFields.get("goodsTitle");
                    if (nameField!=null){
                        Text[] fragments = nameField.getFragments();
                        StringBuilder stringBuffer = new StringBuilder();
                        for (Text str:fragments){
                            stringBuffer.append(str.string());
                        }
                        retrievalResult.setHtmlResult(stringBuffer.toString());


                    }
                }
                arrayList.add(retrievalResult);
//                Map map = objectMapper.readValue(json,Map.class);
//                HighlightField nameField = highlightFields.get("name");
//                arrayList.add(map.get("goodsTitle").toString());
            }
            System.out.println(arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * 获取热度排序的商品列表
     * @param index
     * @param queryBuilder
     * @return
     */
    public ArrayList<GoodsIndex> hotGoodsSort(String index,QueryBuilder queryBuilder){
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.sort("likesNum",SortOrder.DESC);
        searchSourceBuilder.sort("goodsWanter",SortOrder.DESC);
        searchSourceBuilder.sort("viewsNum",SortOrder.DESC);

        SearchResponse searchResponse = null;
        System.out.println(searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        ArrayList<GoodsIndex> goodsIndices = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit:searchHits){
                String json = hit.getSourceAsString();
                goodsIndices.add(toGoodsIndex(json));
//                GoodsIndex goodsIndex = objectMapper.readValue(json,GoodsIndex.class);
//                goodsIndices.add(goodsIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsIndices;
    }

    /**
     * 复合查询商品
     * @param from
     * @param size
     * @param value
     * @param sortType 1:综合查询 2：点赞数查询 3：收藏数查询 4：价格降序 5：价格升序
     * @return
     * @throws IOException
     */
    public ArrayList<GoodsIndex> searchGoods(int from, int size, String value,Integer sortType) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("goodsTitle",value))
                .should(QueryBuilders.matchQuery("goodsDes",value))
                .should(QueryBuilders.matchQuery("goodsType",value))
                .mustNot(QueryBuilders.matchQuery("status","dead"))
                .mustNot(QueryBuilders.matchQuery("status","freezing"));
//                .must(QueryBuilders.matchQuery("status","living"));
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);


        switch (sortType){
            case 1:
                searchSourceBuilder.sort("likesNum",SortOrder.DESC);
                searchSourceBuilder.sort("goodsPrice",SortOrder.DESC);
                searchSourceBuilder.sort("goodsWanter",SortOrder.DESC);
                break;
            case 2:
                searchSourceBuilder.sort("likesNum",SortOrder.DESC);
                break;
            case 3:
                searchSourceBuilder.sort("goodsWanter",SortOrder.DESC);
            case 4:
                searchSourceBuilder.sort("goodsPrice",SortOrder.DESC);
            case 5:
                searchSourceBuilder.sort("goodsPrice",SortOrder.ASC);
        }

        SearchResponse searchResponse = null;
        System.out.println(searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        ArrayList<GoodsIndex> goodsIndices = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit:searchHits){
                String json = hit.getSourceAsString();
                goodsIndices.add(toGoodsIndex(json));
//                GoodsIndex goodsIndex = objectMapper.readValue(json,GoodsIndex.class);
//                goodsIndices.add(goodsIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsIndices;
    }

    public void updateUserInfo(User user){
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("userName",user.getNickname());
        jsonMap.put("userIcon",user.getUser_icon());
        UpdateRequest updateRequest = new UpdateRequest("user",user.getFlea_id()).doc(jsonMap);
        UpdateResponse updateResponse = null;
        try {

            updateResponse = client.update(updateRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //更新商品信息
    public void updateGoodsInfo(Goods goods) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("goodsTitle",goods.getProduct_title());
        jsonMap.put("goodsDes",goods.getProduct_des());
        jsonMap.put("goodsType",(goods.getAtype()+goods.getBtype()+goods.getCtype()+""));
        if (goods.getPhotos().contains(",")){
            jsonMap.put("goodsPhoto",goods.getPhotos().split(",")[0]);
        }else{
            jsonMap.put("goodsPhoto",goods.getPhotos());
        }

        jsonMap.put("goodsPrice",goods.getNow_price());
        UpdateRequest updateRequest = new UpdateRequest("goods",goods.getProduct_id()).doc(jsonMap);
        UpdateResponse updateResponse = null;
        try {

            updateResponse = client.update(updateRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除商品信息
     * @param goodsId
     */
    public void deleteGoodsInfo(String goodsId) {
        DeleteRequest request = new DeleteRequest("goods",goodsId);
        request.timeout("2m");
        try {
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入用户信息
     * @param user
     */
    public IndexResponse insertUser(User user) {
        ESUser esUser = new ESUser();
        esUser.setUserId(user.getFlea_id());
        esUser.setUserCredit(user.getCredit());
        esUser.setUserName(user.getNickname());
        esUser.setUserIcon(user.getUser_icon());
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id(esUser.getUserId());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        IndexResponse index=null;
        try {
            json = objectMapper.writeValueAsString(esUser);
            indexRequest.source(json, XContentType.JSON);
            index = client.index(indexRequest,ElasticSearchConfig.COMMON_OPTIONS);
            System.out.println("响应数据"+index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

//    public ArrayList<ESGoodsTypes> selectBCType(String index,String needType) throws IOException {
//        getTypes(index,"btype");
//        return
//    }

    /**
     * 通过用户id获取商品信息
     * @return
     */
    public ArrayList<GoodsIndex> selectGoodsById(Integer from,Integer size,String userId){

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("userId",userId));
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        ArrayList<GoodsIndex> arrayList =null;
        try {
            SearchResponse searchResponse = client.search(searchRequest,ElasticSearchConfig.COMMON_OPTIONS);
            ObjectMapper objectMapper = new ObjectMapper();
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
            arrayList = new ArrayList<>();
            for (SearchHit hit:searchHits){
                String json = hit.getSourceAsString();
                GoodsIndex goodsIndex = objectMapper.readValue(json,GoodsIndex.class);
                ESUser esUser = selectUserIndex(goodsIndex.getUserId());
                goodsIndex.setUserName(esUser.getUserName());
                goodsIndex.setUserIcon(esUser.getUserIcon());

                arrayList.add(goodsIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;

    }

    /**
     * 统计目标条件的数量
     * @return
     */
    public Integer selectCount(String index,QueryBuilder queryBuilder){
        CountRequest countRequest = new CountRequest();
        countRequest.indices(index);

        countRequest.query(queryBuilder);
        CountResponse countResponse=null;
        int count = 0;
        try {
            countResponse = client.count(countRequest,ElasticSearchConfig.COMMON_OPTIONS);
            count = Integer.parseInt(countResponse.getCount()+"") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return count;
    }
}
