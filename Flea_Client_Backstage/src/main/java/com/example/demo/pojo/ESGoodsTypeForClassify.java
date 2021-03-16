package com.example.demo.pojo;

import java.util.ArrayList;

/**
 * 是一个集合
 * 存储了btype，btype存的ctype集合
 * @author 周杨清
 * @date 2021/3/6 12:08
 */
public class ESGoodsTypeForClassify {
    private String btype;
    private ArrayList<ESGoodsTypes> ctype;

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public ArrayList<ESGoodsTypes> getCtype() {
        return ctype;
    }

    public void setCtype(ArrayList<ESGoodsTypes> ctype) {
        this.ctype = ctype;
    }

    @Override
    public String toString() {
        return "ESGoodsTypeForClassify{" +
                "btype='" + btype + '\'' +
                ", ctype=" + ctype +
                '}';
    }
}
