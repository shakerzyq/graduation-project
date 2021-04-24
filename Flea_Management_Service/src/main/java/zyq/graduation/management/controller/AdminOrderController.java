package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.AdminOrderMapper;
import zyq.graduation.management.pojo.Order;

/**
 * @author 周杨清
 * @date 2021/3/22 18:05
 */
@RestController
@RequestMapping("/order")
public class AdminOrderController {

    @Autowired
    AdminOrderMapper orderMapper;

    /**
     * 根据id获取历史订单信息
     * @param orderId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getOrdersByOrderId/{orderId}")
    public Order getOrders(@PathVariable("orderId") String orderId){
        System.out.println("orderid为"+orderId);
        return orderMapper.selectHistoryOrderByOrderId(orderId);
    }
}
