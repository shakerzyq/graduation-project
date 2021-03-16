package com.example.demo.service.impl;

import com.example.demo.config.ElasticSearchConfig;
import com.example.demo.dao.ESDataDao;
import com.example.demo.pojo.ESGoodsTypeForClassify;
import com.example.demo.pojo.ESGoodsTypes;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.pojo.RetrievalResult;
import com.example.demo.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 周杨清
 * @date 2021/3/4 18:29
 */
@Service
public class IElasticSearchService implements ElasticSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ESDataDao esDataDao;

    @Override
    public ArrayList<ESGoodsTypes> s_getTypesA() throws IOException {
        return esDataDao.getTypes("types_a",QueryBuilders.matchAllQuery(),"atype");
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices("types_a");
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(20);
//
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
//        ObjectMapper objectMapper = new ObjectMapper();
//        SearchHits hits = searchResponse.getHits();
//        SearchHit[] searchHits = hits.getHits();//获取所有命中的记录
//        ArrayList<ESGoodsTypes> arrayList = new ArrayList<>();
//        for(SearchHit hit:searchHits){
//            ESGoodsTypes esGoodsTypes = new ESGoodsTypes();
//            String json = hit.getSourceAsString();
//            //System.out.println(json);
//            Map map = objectMapper.readValue(json,Map.class);
//            esGoodsTypes.setType(map.get("atype").toString());
//            arrayList.add(esGoodsTypes);
//        }
//        System.out.println(arrayList);
//        return arrayList;
    }

    public ArrayList<ESGoodsTypes> s_getTypesB(String atype) throws IOException{
        return esDataDao.getTypes("types_b",QueryBuilders.matchQuery("atype",atype),"btype");
    }

    public ArrayList<ESGoodsTypes> s_getTypesC(String btype) throws IOException{

        return esDataDao.getTypes("types_c",QueryBuilders.matchQuery("btype",btype),"ctype");
    }

    @Override
    public ArrayList<RetrievalResult> s_searchTitle(String content) {
        return esDataDao.searchTitle("goods",QueryBuilders.matchQuery("goodsTitle",content));

    }

    /**
     * 查询商品
     * @param from
     * @param size
     * @param value
     * @param sortType
     * @return
     * @throws IOException
     */
    @Override
    public ArrayList<GoodsIndex> s_searchGoodsList(int from, int size, String value,Integer sortType) throws IOException {
        return esDataDao.searchGoods(from,size,value,sortType);

    }

    @Override
    public ArrayList<ESGoodsTypeForClassify> s_searchBCType(String atype) throws IOException {
        ArrayList<ESGoodsTypeForClassify> esGoodsTypeForClassifies = new ArrayList<>();
        ArrayList<ESGoodsTypes> esGoodsTypesB =  esDataDao.getTypes("types_b",QueryBuilders.matchQuery("atype",atype),"btype");
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
        return esGoodsTypeForClassifies;
    }
}
