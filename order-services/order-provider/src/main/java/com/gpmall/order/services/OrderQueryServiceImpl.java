package com.gpmall.order.services;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.order.OrderQueryService;
import com.gpmall.order.constant.OrderRetCode;
import com.gpmall.order.converter.OrderConverter;
import com.gpmall.order.dal.entitys.*;
import com.gpmall.order.dal.persistence.OrderItemMapper;
import com.gpmall.order.dal.persistence.OrderMapper;
import com.gpmall.order.dal.persistence.OrderShippingMapper;
import com.gpmall.order.dto.*;
import com.gpmall.order.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午10:04
 */
@Slf4j
@Service
public class OrderQueryServiceImpl implements OrderQueryService{

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    OrderShippingMapper orderShippingMapper;

    @Autowired
    OrderConverter orderConverter;


    @Override
    public OrderCountResponse orderCount(OrderCountRequest request) {
        OrderCountResponse response=new OrderCountResponse();
        try {
            OrderExample example = new OrderExample();
            Long count = orderMapper.countByExample(example);
            response.setCount(count.intValue());
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("OrderQueryServiceImpl.orderCount occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public OrderListResponse orderList(OrderListRequest request) {

        return null;
    }

    /**
     * 查询订单明细
     * @param request
     * @return
     */
    @Override
    public OrderDetailResponse orderDetail(OrderDetailRequest request) {
        OrderDetailResponse response=new OrderDetailResponse();
        try{
            request.requestCheck();
            Order order=orderMapper.selectByPrimaryKey(request.getOrderId());
            OrderItemExample example=new OrderItemExample();
            OrderItemExample.Criteria criteria=example.createCriteria();
            criteria.andOrderIdEqualTo(order.getOrderId());
            List<OrderItem> list=orderItemMapper.selectByExample(example);
            OrderShipping orderShipping=orderShippingMapper.selectByPrimaryKey(order.getOrderId());
            response=orderConverter.order2res(order);
            response.setOrderItemDto(orderConverter.item2dto(list));
            response.setOrderShippingDto(orderConverter.shipping2dto(orderShipping));
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
            return response;
        }catch (Exception e){
            log.error("OrderQueryServiceImpl.orderDetail occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
