package com.example.demo.pojo;

/**
 * @author 周杨清
 * @date 2021/3/11 19:15
 */
public class OrdersShow {
    private String goodsTitle;
    private String goodsPicture;
    private String goodsContent;
    private Double goodsPrice;
    private String goodsId;

    private String usersIcon;
    private String userName;
    private String merchantId;
    private String consumerId;
    private String userEmail;

    private String orderId;
    private String orderStatus;
    private String orderDate;
    private String EvaluateContent;
    private String EvaluateLevel;

    private String reportStatus;

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getEvaluateContent() {
        return EvaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        EvaluateContent = evaluateContent;
    }

    public String getEvaluateLevel() {
        return EvaluateLevel;
    }

    public void setEvaluateLevel(String evaluateLevel) {
        EvaluateLevel = evaluateLevel;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getUsersIcon() {
        return usersIcon;
    }

    public void setUsersIcon(String usersIcon) {
        this.usersIcon = usersIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OrdersShow{" +
                "goodsTitle='" + goodsTitle + '\'' +
                ", goodsPicture='" + goodsPicture + '\'' +
                ", goodsContent='" + goodsContent + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsId='" + goodsId + '\'' +
                ", usersIcon='" + usersIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", MerchantId='" + merchantId + '\'' +
                ", ConsumerId='" + consumerId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", EvaluateContent='" + EvaluateContent + '\'' +
                ", EvaluateLevel='" + EvaluateLevel + '\'' +
                '}';
    }
}
