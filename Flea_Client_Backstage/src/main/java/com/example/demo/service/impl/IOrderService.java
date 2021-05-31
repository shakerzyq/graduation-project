package com.example.demo.service.impl;

import com.example.demo.dao.ESDataDao;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.pojo.*;
import com.example.demo.pojo.putpojo.CommitEvaluate;
import com.example.demo.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/3/10 13:29
 */
@Service
public class IOrderService implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ESDataDao esDataDao;

    @Override
    public User s_userInfoToOrder(String userId) {

        return orderMapper.selectUserInfoForOrder(userId);
    }

    /**
     * 生成订单
     * @param order
     * @return
     */
    @Override
    public Boolean s_putOrder(Order order) {
        /*查询商品状态，如果为living就说明还没被下单，执行下面操作*/
        String result = esDataDao.selectGoodsIndex(order.getGoods_id()).getStatus();
        System.out.println("status结果为"+result);
         if(result.equals("living")){
             /*更新商品状态为trading，其它用户就访问不了*/
             esDataDao.updateGoodsStatus("goods",order.getGoods_id(),"trading");
             order.setOrder_id(UUID.randomUUID().toString());
             Date date = new Date();
             DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA);
             DateFormat df2 = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.CHINA);
             order.setOrder_time(df1.format(date)+df2.format(date));
             return orderMapper.insertOrder(order);
         }else {
             return false;
         }

    }

    /**
     * 获取订单信息+用户信息+商品信息
     * @param pageNum
     * @param userId
     * @return
     */
    @Override
    public ArrayList<OrdersShow> s_getOrders(String pageNum, String userId,String orderStatus,String sellorbuy) {


        ArrayList<OrdersShow> orderShowList = new ArrayList<>();
        GoodsIndex goodsIndex=null;
        OrdersShow ordersShow=null;
        String status=null;
        if (orderStatus.equals("todo")){
            ArrayList<Order> orderList = null;
            status="trading";
            if (sellorbuy.equals("buy")){
                orderList= orderMapper.selectOrdersBuy(((Integer.parseInt(pageNum)-1)*8),8,userId);
                for(Order order:orderList){
                    QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                            .should(QueryBuilders.matchQuery("goodsId",order.getGoods_id()))
                            .must(QueryBuilders.matchQuery("status",status));
                    goodsIndex = esDataDao.selectGoodsForOrders("goods", queryBuilder,0,8,order.getConsumer_id());
                    System.out.println("收到的goodsIndex为："+goodsIndex);
                    ordersShow= new OrdersShow();
                    ordersShow.setOrderId(order.getOrder_id());
                    ordersShow.setOrderStatus(order.getOrder_status());
                    ordersShow.setOrderDate(order.getOrder_date());


                    ordersShow.setGoodsPrice(goodsIndex.getGoodsPrice());
                    ordersShow.setGoodsTitle(goodsIndex.getGoodsTitle());
                    ordersShow.setGoodsContent(goodsIndex.getGoodsDes());
                    ordersShow.setGoodsPicture(goodsIndex.getGoodsPhoto());
                    ordersShow.setGoodsId(goodsIndex.getGoodsId());

                    ordersShow.setUserName(goodsIndex.getUserName());
                    ordersShow.setUsersIcon(goodsIndex.getUserIcon());
                    ordersShow.setMerchantId(goodsIndex.getUserId());
                    ordersShow.setConsumerId(order.getConsumer_id());

                    ordersShow.setUserEmail(orderMapper.selectUserEmail(order.getMerchant_id()));



                    orderShowList.add(ordersShow);
                }
            }else {
                orderList= orderMapper.selectOrdersSell(((Integer.parseInt(pageNum)-1)*8),8,userId);
                for(Order order:orderList){
                    QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                            .should(QueryBuilders.matchQuery("goodsId",order.getGoods_id()))
                            .must(QueryBuilders.matchQuery("status",status));
                    goodsIndex = esDataDao.selectGoodsForOrders("goods", queryBuilder,0,8,order.getConsumer_id());
                    System.out.println("收到的goodsIndex为："+goodsIndex);
                    ordersShow= new OrdersShow();
                    ordersShow.setOrderId(order.getOrder_id());
                    ordersShow.setOrderStatus(order.getOrder_status());
                    ordersShow.setOrderDate(order.getOrder_date());


                    ordersShow.setGoodsPrice(goodsIndex.getGoodsPrice());
                    ordersShow.setGoodsTitle(goodsIndex.getGoodsTitle());
                    ordersShow.setGoodsContent(goodsIndex.getGoodsDes());
                    ordersShow.setGoodsPicture(goodsIndex.getGoodsPhoto());
                    ordersShow.setGoodsId(goodsIndex.getGoodsId());

                    ordersShow.setUserName(goodsIndex.getUserName());
                    ordersShow.setUsersIcon(goodsIndex.getUserIcon());
                    ordersShow.setMerchantId(goodsIndex.getUserId());
                    ordersShow.setConsumerId(order.getConsumer_id());

                    ordersShow.setUserEmail(orderMapper.selectUserEmail(order.getConsumer_id()));



                    orderShowList.add(ordersShow);
                }
            }


        }else{
            ArrayList<HistoryOrder> historyOrders = null;
            status="dead";
            if (sellorbuy.equals("buy")){
                historyOrders= orderMapper.selectHistoryOrderBuy(((Integer.parseInt(pageNum)-1)*8),8,userId);

            }else {
                historyOrders= orderMapper.selectHistoryOrderSell(((Integer.parseInt(pageNum)-1)*8),8,userId);
            }
            for(HistoryOrder historyOrder:historyOrders){
                QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("goodsId",historyOrder.getGoods_id()))
                        .must(QueryBuilders.matchQuery("status",status));
                goodsIndex = esDataDao.selectGoodsForOrders("goods", queryBuilder,0,8,historyOrder.getConsumer_id());
                if (goodsIndex==null){
                    ordersShow= new OrdersShow();
                    ordersShow.setOrderId(historyOrder.getOrder_id());
                    ordersShow.setOrderStatus("交易完成");
                    ordersShow.setOrderDate(historyOrder.getOrder_date());
                    ordersShow.setEvaluateContent(historyOrder.getEvaluate_content());
                    ordersShow.setEvaluateLevel(historyOrder.getEvaluate_level());

                    ordersShow.setGoodsPrice(0.00);
                    ordersShow.setGoodsContent("该商品已被删除");
                    ordersShow.setGoodsPicture("该商品已被删除");
                    ordersShow.setGoodsId(historyOrder.getGoods_id());

                    ordersShow.setUserName("");
                    ordersShow.setUsersIcon("");
                    ordersShow.setMerchantId(historyOrder.getMerchant_id());
                    ordersShow.setConsumerId(historyOrder.getConsumer_id());

                    ordersShow.setReportStatus(orderMapper.selectReportStatus(ordersShow.getOrderId(),userId));
                    orderShowList.add(ordersShow);
                }else{
                    System.out.println("收到的goodsIndex为："+goodsIndex);
//            assert ordersShow != null;
//            assert false;
                    ordersShow= new OrdersShow();
                    ordersShow.setOrderId(historyOrder.getOrder_id());
                    ordersShow.setOrderStatus("交易完成");
                    ordersShow.setOrderDate(historyOrder.getOrder_date());
                    ordersShow.setEvaluateContent(historyOrder.getEvaluate_content());
                    ordersShow.setEvaluateLevel(historyOrder.getEvaluate_level());

                    ordersShow.setGoodsPrice(goodsIndex.getGoodsPrice());
                    ordersShow.setGoodsContent(goodsIndex.getGoodsDes());
                    ordersShow.setGoodsPicture(goodsIndex.getGoodsPhoto());
                    ordersShow.setGoodsId(goodsIndex.getGoodsId());

                    ordersShow.setUserName(goodsIndex.getUserName());
                    ordersShow.setUsersIcon(goodsIndex.getUserIcon());
                    ordersShow.setMerchantId(goodsIndex.getUserId());
                    ordersShow.setConsumerId(historyOrder.getConsumer_id());

                    ordersShow.setReportStatus(orderMapper.selectReportStatus(ordersShow.getOrderId(),userId));
                    orderShowList.add(ordersShow);
                }

            }

        }
        return orderShowList;

    }





    /**
     * 删除订单
     * 分为历史订单和待处理订单
     * 为待处理订单：es中内容改为living
     * 若为历史订单则不变
     * @param orderId
     * @param type
     * @return
     */
    @Override
    public Boolean s_deleteOrder(String flea_id,String orderId, String type,String goodsId,String orderStatus) {
        if (type.equals("todo")){
            esDataDao.updateGoodsStatus("goods",goodsId,"living");
            if (orderStatus.equals("交易中")){
                orderMapper.downUserCredit(flea_id,5.0);
            }else if(orderStatus.equals("待接单")){
                orderMapper.downUserCredit(flea_id,3.0);
            }
            return orderMapper.deleteOrder(orderId);
        }else{
            return orderMapper.deleteHistoryOrder(orderId);
        }
    }

    /**
     * 更新订单，修改订单
     * @param orderId
     * @return
     */
    @Override
    public Boolean s_updateOrder(String flea_id,String orderId,String tradingPlace,String orderDate) {
        /*扣除用户的信誉积分*/
        orderMapper.downUserCredit(flea_id,2.5);
        return orderMapper.updateOrder(orderId,tradingPlace,orderDate);
    }

    /**
     * 交易完成，用户点击确认收货
     * 将orders中的订单信息转移到historyorder中
     * @param commitEvaluate
     * @return
     */
    @Override
    public Boolean s_accomplishOrder(CommitEvaluate commitEvaluate) {
        Order order = orderMapper.selectOrdersByOrderId(commitEvaluate.getOrderId());

//        Date date = new Date();
//        DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA);
//        DateFormat df2 = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.CHINA);
//        order.setOrder_date(df1.format(date)+df2.format(date));
        int evaluateScore =  commitEvaluate.getGoodsEvaluate()+commitEvaluate.getMerchantEvaluate();
        String evaluate = null;
        if (commitEvaluate.getMerchantEvaluate()>=4&&commitEvaluate.getGoodsEvaluate()>=4){
            orderMapper.upUserCredit(commitEvaluate.getMerchantId(),5.0);
            evaluate="特别好评";

        }else if (evaluateScore>=6){
            orderMapper.upUserCredit(commitEvaluate.getMerchantId(),3.0);
            evaluate="好评";
        }else if (evaluateScore>=5){
            orderMapper.upUserCredit(commitEvaluate.getMerchantId(),1.0);
            evaluate="一般";
        }else{
            evaluate="差评";
            orderMapper.downUserCredit(commitEvaluate.getMerchantId(),5.0);
        }
        Boolean aBoolean = orderMapper.insertHistoryOrder(order, commitEvaluate.getEvaluateContent(), evaluate);
        if (aBoolean){
            /*更新es中的goods为dead*/
            esDataDao.updateGoodsStatus("goods",commitEvaluate.getGoodsId(),"dead");
            return orderMapper.deleteOrder(commitEvaluate.getOrderId());

        }
        return false;
    }


}
