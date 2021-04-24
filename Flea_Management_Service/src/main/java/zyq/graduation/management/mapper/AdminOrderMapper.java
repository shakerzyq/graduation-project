package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zyq.graduation.management.pojo.Order;

import java.util.ArrayList;

/**
 *
 * @author 周杨清
 * @date 2021/3/22 18:04
 */
@Mapper
public interface AdminOrderMapper {
    @Select("select * from history_orders where order_id=#{orderId}")
    Order selectHistoryOrderByOrderId(String orderId);

    @Delete("delete from orders where (consumer_id=#{flea_id} or merchant_id=#{flea_id}) and (order_status='交易中' or order_status='待接单') ")
    Boolean deleteOrder(String flea_id);
}
