package com.example.demo.pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 发布更新物品
 * @author 周杨清
 * @date 2021/2/21 11:47
 */
@Component
public class Products implements Serializable{
    private String product_id;
    private String merchant_id;
    private String product_des;
    private String quality;
    private String class_choice;
    private Double old_price;
    private Double now_price;
    private String photos;

    @Override
    public String toString() {
        return "Products{" +
                "product_id='" + product_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", product_des='" + product_des + '\'' +
                ", quality='" + quality + '\'' +
                ", class_choice='" + class_choice + '\'' +
                ", old_price='" + old_price + '\'' +
                ", now_price='" + now_price + '\'' +
                ", photos='" + photos + '\'' +
                '}';
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getProduct_des() {
        return product_des;
    }

    public void setProduct_des(String product_des) {
        this.product_des = product_des;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getClass_choice() {
        return class_choice;
    }

    public void setClass_choice(String class_choice) {
        this.class_choice = class_choice;
    }

    public Double getOld_price() {
        return old_price;
    }

    public void setOld_price(Double old_price) {
        this.old_price = old_price;
    }

    public Double getNow_price() {
        return now_price;
    }

    public void setNow_price(Double now_price) {
        this.now_price = now_price;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
//        String pics="";
//        for (String str:photos){
////            if (pics.equals("")||pics.length()==0){
////                pics=str;
////            }else{
////                pics=pics+str;
////            }
//            pics=pics+str;
//        }
        this.photos=photos ;
    }
}
