package com.example.demo;

import com.example.demo.config.ElasticSearchConfig;
import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.BrowseLogMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.TransDataMapper;
import com.example.demo.pojo.*;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    TransDataMapper transDataMapper;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ESDataDao esDataDao;

    @Test
    void contextLoads() {
    }



    @Test
    public void IteratorTest(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        for (String str :list){
            if (str.equals("b")){
                list.remove(str);
            }
        }

        for (String str : list){
            System.out.println(str);
        }

        //System.out.println(System.currentTimeMillis());
       /* long timeold = Long.parseLong("1614323196956") ;

        long timenow = Long.parseLong("1614326196956") ;
        long day = Long.parseLong("86400000") ;
        System.out.println((timenow-timeold)/day);*/
//        ArrayList<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//
//        Iterator<String> iterator = list.iterator();
//        while(iterator.hasNext()){
//            System.out.println("a"+iterator.next());
//
//            System.out.println("b"+iterator.next());
//        }
//
//
//
//
//        /*for(String str:list){
//            list.
//        }*/
//
//        Iterator<String> iterator = list.iterator();
//        while(iterator.hasNext()){
//            System.out.println("a"+iterator.next());
//
//            System.out.println("b"+iterator.next());
//        }



    }

    @Test
    public void tansData() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        list.add("运动");
        list.add("数码");
        list.add("洗护");
        list.add("箱包");
        list.add("户外");
        list.add("男装");
        list.add("女装");
        list.add("食品");
        list.add("配饰");
        list.add("美妆");
        list.add("植物宠物");
        list.add("男鞋");
        list.add("女鞋");
        IndexRequest indexRequest = new IndexRequest("types");
        for (int i=1;i<=list.size();i++){
            indexRequest.id(i+"");
            indexRequest.source("mtype",list.get(i-1));
            client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        }
//        IndexRequest indexRequest = new IndexRequest("goods_main_");
//        indexRequest.id()
//        IndexResponse index =
//        System.out.println(index);
    }

    //获取atype
    @Test
    public void test3() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("types_a");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        ObjectMapper objectMapper = new ObjectMapper();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
        ArrayList<String> arrayList = new ArrayList<>();
        for(SearchHit hit:searchHits){
            String json = hit.getSourceAsString();
            //System.out.println(json);
            Map map = objectMapper.readValue(json,Map.class);
            arrayList.add(map.get("atype").toString());
        }
        System.out.println(arrayList);
    }

    //获取btype
    @Test
    public void test4() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("types_b");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(QueryBuilders.matchQuery("atype","数码"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        ObjectMapper objectMapper = new ObjectMapper();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
        ArrayList<String> arrayList = new ArrayList<>();
        for(SearchHit hit:searchHits){
            String json = hit.getSourceAsString();
            //System.out.println(json);
            Map map = objectMapper.readValue(json,Map.class);
            arrayList.add(map.get("btype").toString());
        }
        System.out.println(arrayList);
    }

    @Test
    public void  test(){
        String str ="safdsf";
        System.out.println(str.indexOf(","));
    }

    @Test
    public void test5(){
        System.out.println(esDataDao.selectGoods("goods", QueryBuilders.matchAllQuery(),0,20));

    }

    /**
     * 获取
     */
    @Test
    public void test6() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GetRequest getRequest = new GetRequest("goods","82aa6688-3f51-4bf4-b8a6-9acf2ffaf5dc");
        GetResponse getResponse = client.get(getRequest,ElasticSearchConfig.COMMON_OPTIONS);
        String sourceAsString = getResponse.getSourceAsString();
        GoodsIndex goodsIndex =objectMapper.readValue(sourceAsString,GoodsIndex.class);

        System.out.println(goodsIndex.getGoodsWanter());
    }

    /**
     * 更新
     */
    @Test
    public void test7() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("goodsWanter", 1);

        UpdateRequest updateRequest = new UpdateRequest("goods","82aa6688-3f51-4bf4-b8a6-9acf2ffaf5dc").doc(jsonMap);

        UpdateResponse updateResponse = client.update(updateRequest,ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(updateResponse);
    }

    /**
     * 检索
     */
    @Test
    public void test8(){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("goodsTitle","英雄联盟"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(50);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(true).field("goodsTitle")
                .preTags("<strong>").postTags("</strong>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        ArrayList<String> arrayList =null;
        try {
            searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            ObjectMapper objectMapper = new ObjectMapper();
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
            arrayList = new ArrayList<>();
            for(SearchHit hit:searchHits){
                String json = hit.getSourceAsString();
                Map map = objectMapper.readValue(json,Map.class);
                arrayList.add(map.get("goodsTitle").toString());
            }
            System.out.println(arrayList);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现多重查询
     */
    @Test
    public void test9() throws IOException {
        MultiSearchRequest request = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("goodsTitle","英雄联盟"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);
        System.out.println("检索一:"+searchSourceBuilder.toString());


        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery("goodsDes","英雄联盟"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        secondSearchRequest.source(searchSourceBuilder);
        System.out.println("检索二:"+searchSourceBuilder.toString());
        request.add(secondSearchRequest);


        System.out.println(request.toString());
        MultiSearchResponse getResponse = client.msearch(request,ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(getResponse);
    }

    /**
     * 实现复合查询
     */
    @Test
    public void test10(){
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchRequest.source(searchSourceBuilder);
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("goodsTitle","英雄联盟"))
                .should(QueryBuilders.matchQuery("goodsDes","英雄联盟"));
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.sort("likesNum", SortOrder.DESC);
        SearchResponse search = null;
//        System.out.println(searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            search = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(search);
    }

    @Test
    public void test11() throws IOException {
        ArrayList<ESGoodsTypeForClassify> esGoodsTypeForClassifies = new ArrayList<>();
        ArrayList<ESGoodsTypes> esGoodsTypesB =  esDataDao.getTypes("types_b",QueryBuilders.matchQuery("atype","数码"),"btype");
        ArrayList<ESGoodsTypes> esGoodsTypesC = null;
        ESGoodsTypeForClassify esGoodsTypeForClassify=null;
        for (ESGoodsTypes esGoodsTypes:esGoodsTypesB){
            esGoodsTypeForClassify = new ESGoodsTypeForClassify();
            esGoodsTypeForClassify.setBtype(esGoodsTypes.getType());
            if (!esGoodsTypes.getType().equals("其它")&&!esGoodsTypes.getType().equals("其他")){
                esGoodsTypesC = esDataDao.getTypes("types_c",QueryBuilders.matchQuery("btype",esGoodsTypes.getType()),"ctype");
            }else{
                esGoodsTypesC=null;
            }
            esGoodsTypeForClassify.setCtype(esGoodsTypesC);
            esGoodsTypeForClassifies.add(esGoodsTypeForClassify);
        }
        for (ESGoodsTypeForClassify classify : esGoodsTypeForClassifies){
            System.out.println(classify);
        }
    }

    /**
     * 时间转换
     */
    @Test
    public void test12(){
        Date date = new Date(Long.parseLong("1615015997118"));
        System.out.println(date);
    }

    @Test
    public void test13(){
        DeleteRequest request = new DeleteRequest("goods","2b369252-b080-4c51-bd89-044cb0885c74");
        request.timeout("2m");
        try {
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入用户信息
     */
    @Test
    public void test14(){
        User user = new User();
        user.setFlea_id("1111");
        user.setCredit(100.0);
        user.setNickname("sdf");
        user.setUser_icon("fsaf");
        esDataDao.insertUser(user);
    }
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void test15(){
        esDataDao.updateGoodsStatus("goods","5579fa87-f8be-49db-abfb-4089a75b79e8","living");
    }

    /**
     * 查询总数
     */
    @Test
    public void test16(){
        CountRequest countRequest = new CountRequest();
        countRequest.indices("goods");
        countRequest.query(QueryBuilders.termQuery("status","living"));
        CountResponse countResponse=null;
        try {
            countResponse = client.count(countRequest,ElasticSearchConfig.COMMON_OPTIONS);
            countResponse.getCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(countResponse);
    }


    /**
     * 测试对用户信誉值的增减
     */
    @Test
    public void test17(){
        orderMapper.downUserCredit("7a0fc93fe",10.0);
    }

    @Autowired
    OrderService orderService;

    @Test
    public void test18(){
//        Order order = orderMapper.selectOrdersByOrderId("bfa50c2f-e3a0-44ed-97e2-e6ca5e549412");
//        System.out.println(order);
//        System.out.println(orderService.s_accomplishOrder("bfa50c2f-e3a0-44ed-97e2-e6ca5e549412"));
    }

    /*md5加密*/
    @Test
    public void test19(){
        String pwd = "123455";
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bs = instance.digest(pwd.getBytes());
            String str = "";
            //第一，将所有的数据，转换成正数
            for (byte b : bs) {
                int temp = b & 255;
            //第二，将所有的数据，转换成16进制格式
                if(temp >=0 && temp <=15){
                    str = str +"0"+ Integer.toHexString(temp);
                }else{
                    str = str + Integer.toHexString(temp);;
                }
            }
            System.out.println(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    BrowseLogMapper browseLogMapper;

    @Test
    public void test20(){
        System.out.println(browseLogMapper.selectBrowseLog("db3a3b065"));
    }

    /**
     * 冒泡排序
     */
    @Test
    public void sort(){
        int j=0;
        int stemp=0;
        int[] arr = {1,4,2,9,5,6,3,8,7,0};
        for(int i=0;i<arr.length;i++){
            for(j=i+1;j<arr.length;j++){
                if (arr[j]>arr[i]){
                    stemp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=stemp;
                }
            }
        }
        for(int a:arr){
            System.out.println(a);
        }

    }


    /**
     * 验证用户信誉值加减
     */
    @Test
    public void test21(){
        orderMapper.upUserCredit("7a0fc93fe",3.0);
    }
}
