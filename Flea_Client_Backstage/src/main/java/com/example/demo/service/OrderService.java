package com.example.demo.service;

import com.example.demo.pojo.Order;
import com.example.demo.pojo.OrdersShow;
import com.example.demo.pojo.User;
import com.example.demo.pojo.putpojo.CommitEvaluate;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/10 13:29
 */
public interface OrderService {
    /**
     * 获取用户信心，主要为微信号
     * @param userId
     * @return
     */
    User s_userInfoToOrder(String userId);

    /**
     * 插入用户信息
     * @param order
     */
    Boolean s_putOrder(Order order);

    /**
     * 获取订单信息
     * @param pageNum
     * @param userId
     * @return
     */
    ArrayList<OrdersShow> s_getOrders(String pageNum, String userId, String orderStatus, String sellorbuy);

    Boolean s_deleteOrder(String flea_id,String orderId, String type,String goodsId,String orderStatus);

    /**
     * 更新订单，修改订单
     * @param orderId
     * @return
     */
    Boolean s_updateOrder(String flea_id,String orderId,String tradingPlace,String orderDate);

    /**
     * 交易完成，用户点击确认收货
     * @param commitEvaluate
     * @return
     */
    Boolean s_accomplishOrder(CommitEvaluate commitEvaluate);
}
