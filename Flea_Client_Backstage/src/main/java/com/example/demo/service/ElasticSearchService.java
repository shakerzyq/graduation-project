package com.example.demo.service;

import com.example.demo.pojo.ESGoodsTypeForClassify;
import com.example.demo.pojo.ESGoodsTypes;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.pojo.RetrievalResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/4 18:27
 */
@Component
public interface ElasticSearchService {
    /**
     * 获取atype中的类型
     * @return
     * @throws IOException
     */
    public ArrayList<ESGoodsTypes> s_getTypesA() throws IOException;

    /**
     * 获取btype中的类型
     * @param atype
     * @return
     * @throws IOException
     */
    public ArrayList<ESGoodsTypes> s_getTypesB(String atype) throws IOException;

    /**
     * 获取ctype中的类型
     * @param btype
     * @return
     * @throws IOException
     */
    public ArrayList<ESGoodsTypes> s_getTypesC(String btype) throws IOException;

    ArrayList<RetrievalResult> s_searchTitle(String content);

    ArrayList<GoodsIndex> s_searchGoodsList(int from, int size, String value,Integer sortType) throws IOException;

    ArrayList<ESGoodsTypeForClassify> s_searchBCType(String atype) throws IOException;

}
