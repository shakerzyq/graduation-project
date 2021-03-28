package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/2/22 16:58
 */
@Component
public class Goods {
    private String product_id;
    private String merchant_id;
    private String product_title;
    private String product_des;
    private String quality;
    private String class_choice;
    private String atype;
    private String btype;
    private String ctype;
    private String brand;//商品的品牌
    private Double old_price;
    private Double now_price;
    private String  photos;
    private Long add_date;
    private String add_place;
    private Integer views;

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

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
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

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
        this.photos = photos;
    }

    public Long getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Long add_date) {
        this.add_date = add_date;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "product_id='" + product_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", product_des='" + product_des + '\'' +
                ", quality='" + quality + '\'' +
                ", class_choice='" + class_choice + '\'' +
                ", atype='" + atype + '\'' +
                ", btype='" + btype + '\'' +
                ", ctype='" + ctype + '\'' +
                ", brand='" + brand + '\'' +
                ", old_price=" + old_price +
                ", now_price=" + now_price +
                ", photos='" + photos + '\'' +
                ", add_time=" + add_date +
                ", views=" + views +
                '}';
    }
}
