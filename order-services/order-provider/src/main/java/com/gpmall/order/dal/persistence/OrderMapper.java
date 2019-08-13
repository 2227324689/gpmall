package com.gpmall.order.dal.persistence;

import java.util.List;

import com.gpmall.order.dal.entitys.Order;
import com.gpmall.order.dal.entitys.OrderExample;
import com.gpmall.order.dal.entitys.OrderList;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<OrderList> selectOrderListByUserId(@Param("uid")Long uid);
}