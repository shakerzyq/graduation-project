package com.example.demo.pojo.putpojo;

/**
 * 用于提交商品评分的pojo
 * @author 周杨清
 * @date 2021/3/14 14:35
 */
public class CommitEvaluate {

    private Integer merchantEvaluate;
    private Integer goodsEvaluate;
    private String evaluateContent;
    private String orderId;
    private String goodsId;
    private String merchantId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getMerchantEvaluate() {
        return merchantEvaluate;
    }

    public void setMerchantEvaluate(Integer merchantEvaluate) {
        this.merchantEvaluate = merchantEvaluate;
    }

    public Integer getGoodsEvaluate() {
        return goodsEvaluate;
    }

    public void setGoodsEvaluate(Integer goodsEvaluate) {
        this.goodsEvaluate = goodsEvaluate;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "CommitEvaluate{" +
                "merchantEvaluate=" + merchantEvaluate +
                ", goodsEvaluate=" + goodsEvaluate +
                ", evaluateContent='" + evaluateContent + '\'' +
                ", orderId='" + orderId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}
