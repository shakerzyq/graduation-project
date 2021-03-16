package com.example.demo.controller;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.OrdersShow;
import com.example.demo.pojo.User;
import com.example.demo.pojo.putpojo.CommitEvaluate;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/10 13:23
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 下单页面
     * 查出商家信息
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/userInfo/{userId}")
    public User userInfoToOrder(@PathVariable("userId") String userId){
        System.out.println("userId为："+userId);
        User user = orderService.s_userInfoToOrder(userId);
        System.out.println("查询出的user为："+user);
        return user;
    }

    /**
     * 发布订单
     * 发布订单后，商品状态变为trading，其它用户访问不了
     * @param order
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping("/putOrder")
    public Boolean putOrder(@RequestBody Order order){
        System.out.println("order为："+order);
        Boolean judge=  orderService.s_putOrder(order);
        System.out.println(judge);
        return judge;
//        return true;
    }

    /**
     * 查询订单信息
     * @param pageNum
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getOrders/{pageNum}/{userId}/{orderStatus}/{sellorbuy}")
    public ArrayList<OrdersShow> getOrders(@PathVariable("pageNum") String pageNum
            , @PathVariable("userId") String userId
            , @PathVariable("orderStatus") String orderStatus
            , @PathVariable("sellorbuy") String sellorbuy
            ){
        System.out.println("收取到的userId为："+userId+"  "+pageNum+"  "+orderStatus+"  "+sellorbuy);
        return orderService.s_getOrders(pageNum,userId,orderStatus,sellorbuy);
    }

    /**
     * 删除订单
     * 分为历史订单和待处理订单
     * @param orderId
     * @param type  history，todo
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @DeleteMapping("/deleteOrder/{flea_id}/{orderId}/{type}/{goodsId}/{orderStatus}")
    public Boolean deleteOrder(
             @PathVariable("flea_id") String flea_id
            ,@PathVariable("orderId") String orderId
            ,@PathVariable("type") String type
            ,@PathVariable("goodsId") String goodsId
            ,@PathVariable("orderStatus") String orderStatus){
        System.out.println("收到的订单id为："+orderId);
        return orderService.s_deleteOrder(flea_id,orderId,type,goodsId,orderStatus);
    }

    /**
     * 更新订单，修改订单
     * @param orderId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/updateOrder/{flea_id}/{orderId}/{tradingPlace}/{orderDate}")
    public Boolean updateOrder(
            @PathVariable("flea_id") String flea_id
            ,@PathVariable("orderId") String orderId
            ,@PathVariable("tradingPlace") String tradingPlace
            ,@PathVariable("orderDate") String orderDate){
        System.out.println("修改订单收到的orderId为："+orderId);
        return orderService.s_updateOrder(flea_id,orderId,tradingPlace,orderDate);
    }

    /**
     * 交易完成，用户点击确认收货
     * @param
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/accomplishOrder")
    public Boolean accomplishOrder(@RequestBody CommitEvaluate commitEvaluate){
        System.out.println("确认订单收到的orderId为："+commitEvaluate);
        return orderService.s_accomplishOrder(commitEvaluate);
    }

    /**
     * 获取订单详细信息
     * @param orderId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getOrderDetailInfo/{orderId}/{status}")
    public Object getOrderDetailInfo(@PathVariable("orderId") String orderId,@PathVariable("status") String status){
        System.out.println("id为："+orderId+" status为："+status);
        if (status.equals("交易完成")){
            return orderMapper.selectHistoryOrderById(orderId);
        }else{
            return orderMapper.selectOrdersByOrderId(orderId);
        }

    }

    /**
     * 商家接受订单
     * @param orderId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/acceptOrder/{orderId}")
    public Boolean acceptOrder(@PathVariable("orderId") String orderId){
        return orderMapper.updateOrderStatus(orderId);
    }


}
