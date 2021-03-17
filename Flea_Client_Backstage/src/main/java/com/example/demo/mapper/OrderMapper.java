package com.example.demo.mapper;

import com.example.demo.pojo.HistoryOrder;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/10 13:31
 */
@Mapper
public interface OrderMapper {

    @Select("select nickname,user_icon,weixin,credit,email from user where flea_id=#{userId}")
    User selectUserInfoForOrder(String userId);

    @Insert("insert into orders (order_id,goods_id,consumer_id,merchant_id,order_time,trading_place,order_status,order_date,remark) " +
            "values (#{order.order_id},#{order.goods_id},#{order.consumer_id},#{order.merchant_id},#{order.order_time},#{order.trading_place},#{order.order_status},#{order.order_date},#{order.remark})")
    Boolean insertOrder(@Param("order") Order order);

    /**
     * 查询orders表，获取Orders
     * buy
     * @param from
     * @param size
     * @param userId
     * @return
     */
    @Select("select * from orders where consumer_id=#{userId} limit #{from},#{size}")
    ArrayList<Order> selectOrdersBuy(Integer from,Integer size,String userId);

    /**
     * 查询orders表，获取Orders
     * sell
     * @param from
     * @param size
     * @param userId
     * @return
     */
    @Select("select * from orders where merchant_id=#{userId} limit #{from},#{size}")
    ArrayList<Order> selectOrdersSell(Integer from,Integer size,String userId);

    /**
     * 查询historu_orders表，获取Orders
     * buy
     * @param from
     * @param size
     * @param userId
     * @return
     */
    @Select("select * from history_orders where consumer_id=#{userId} limit #{from},#{size}")
    ArrayList<HistoryOrder> selectHistoryOrderBuy(int from, int size, String userId);

    /**
     * 查询historu_orders表，获取Orders
     * sell
     * @param from
     * @param size
     * @param userId
     * @return
     */
    @Select("select * from history_orders where merchant_id=#{userId} limit #{from},#{size}")
    ArrayList<HistoryOrder> selectHistoryOrderSell(int from, int size, String userId);

    /**
     * 删除待处理订单
     * @param orderId
     * @return
     */
    @Delete("delete from orders where order_id=#{orderId}")
    Boolean deleteOrder(String orderId);

    /**
     * 删除历史订单
     * @param orderId
     * @return
     */
    @Delete("delete from history_orders where order_id=#{orderId}")
    Boolean deleteHistoryOrder(String orderId);

    /**
     * 更新订单，修改订单
     * @param orderId
     * @return
     */
    @Update("update orders set order_date=#{orderDate},trading_place=#{tradingPlace} where order_id=#{orderId}")
    Boolean updateOrder(String orderId,String tradingPlace,String orderDate);

    @Delete("delete from orders where order_id=#{orderId}")
    Order deleteOrder2(String orderId);

    @Select("select * from orders where order_id=#{orderId}")
    Order selectOrdersByOrderId(String orderId);

    @Select("select * from history_orders where order_id=#{orderId}")
    HistoryOrder selectHistoryOrderById(String orderId);
    
    @Insert("insert into history_orders (order_id,goods_id,consumer_id,merchant_id,order_time,trading_place,order_status,order_date,remark,evaluate_content,evaluate_level) values " +
            "(#{order.order_id},#{order.goods_id},#{order.consumer_id},#{order.merchant_id},#{order.order_time}," +
            "#{order.trading_place},'交易完成',#{order.order_date},#{order.remark},#{evaluateContent},#{evaluate})")
    Boolean insertHistoryOrder(@Param("order") Order order,@Param("evaluateContent") String evaluateContent,@Param("evaluate") String evaluate);

    /**
     * 商家接受订单，修改订单状态
     * @param orderId
     * @return
     */
    @Update("update orders set order_status='交易中' where order_id=#{orderId}")
    Boolean updateOrderStatus(String orderId);

    @Update("update user set credit=credit-#{num} where flea_id=#{flea_id}")
    void downUserCredit(String flea_id,Double num);

    @Update("update user set credit=credit+#{num} where flea_id=#{merchantId}")
    void upUserCredit(String merchantId, Double num);

    @Select("select email from account where flea_id=#{userid}")
    String selectUserEmail(String userid);
}
