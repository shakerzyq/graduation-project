package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/10 17:38
 */
@Component
public class Order {
    private String order_id;
    private String consumer_id;
    private String merchant_id;
    private String goods_id;
    private String order_time;//订单生成时间
    private String order_date;//交易时间
    private String trading_place;
    private String order_status;
    private String remark;//交易备注

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTrading_place() {
        return trading_place;
    }

    public void setTrading_place(String trading_place) {
        this.trading_place = trading_place;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                ", consumer_id='" + consumer_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", order_time=" + order_time +
                ", order_date='" + order_date + '\'' +
                ", trading_place='" + trading_place + '\'' +
                ", order_status='" + order_status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
