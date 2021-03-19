package com.example.demo.controller;

import com.example.demo.pojo.ESGoodsTypeForClassify;
import com.example.demo.pojo.ESGoodsTypes;
import com.example.demo.pojo.GoodsIndex;
import com.example.demo.pojo.RetrievalResult;
import com.example.demo.service.ElasticSearchService;
import com.sun.javaws.IconUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/4 17:53
 */
@RestController
@RequestMapping("elasticsearch")
public class ESSearchTypeController {

    @Autowired
    ElasticSearchService elasticSearchService;

    /**
     * 获取atype中的类型
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getTypesA")
    public ArrayList<ESGoodsTypes> getTypesA() throws IOException {
        System.out.println("获取types_a");
        return elasticSearchService.s_getTypesA();
    }

    /**
     * 获取btype中的类型
     * @param atype
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getTypesB/{atype}")
    public ArrayList<ESGoodsTypes> getTypesB(@PathVariable("atype") String atype) throws IOException {

        return elasticSearchService.s_getTypesB(atype);
    }

    /**
     * 获取ctype中的类型
     * @param btype
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getTypesC/{btype}")
    public ArrayList<ESGoodsTypes> getTypesC(@PathVariable("btype") String btype) throws IOException {

        return elasticSearchService.s_getTypesC(btype);
    }

    /**
     * 通过输入内容检索title
     * @param content
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/searchTitle/{content}")
    public ArrayList<RetrievalResult> searchTitle(@PathVariable("content") String content){
        return elasticSearchService.s_searchTitle(content);
    }

    /**
     * 服务于search搜索界面
     * @param pagenum
     * @param value
     * @param sortType
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/searchGoodsList/{pagenum}/{value}/{sortType}")
    public ArrayList<GoodsIndex> searchGoodsList(@PathVariable("pagenum")String pagenum, @PathVariable("value") String value, @PathVariable("sortType") String sortType) throws IOException {
        System.out.println("搜索内容为："+value+" pagenum为："+pagenum+"搜索类型为"+sortType);
        //获取所有商品
//        ArrayList<GoodsIndex> result = iForGoodsService.getGoods(((Integer.parseInt(pagenum)-1)*8),8);
        ArrayList<GoodsIndex> result =elasticSearchService.s_searchGoodsList(((Integer.parseInt(pagenum)-1)*8),8,value,Integer.parseInt(sortType));
        System.out.println(result);
        return result;
    }


    /**
     * 服务classify页面
     * 通过atype查找到btype和ctype集合的组合对象的集合
     * @param atype
     * @return
     * @throws IOException
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/searchBCType/{atype}")
    public ArrayList<ESGoodsTypeForClassify> searchBCType(@PathVariable("atype") String atype) throws IOException {
        System.out.println("atype为："+atype);
        return elasticSearchService.s_searchBCType(atype);
    }

}
