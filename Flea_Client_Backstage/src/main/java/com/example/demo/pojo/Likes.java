package com.example.demo.pojo;

import sun.rmi.runtime.Log;

/**
 * @author 周杨清
 * @date 2021/3/13 3:51
 */
public class Likes {
    private String product_id;
    private String consumer_id;
    private String merchant_id;
    private Long like_date;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Long getLike_date() {
        return like_date;
    }

    public void setLike_date(Long like_date) {
        this.like_date = like_date;
    }
}
