package com.gpmall.order.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
            Long count = orderMapper.countAll();
            response.setCount(count.intValue());
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("OrderQueryServiceImpl.orderCount occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    /**
     * 查询历史订单列表
     * @param request
     * @return
     * @author GP17513-成都-Rigel
     */
    @Override
    public OrderListResponse orderList(OrderListRequest request) {
        OrderListResponse response = new OrderListResponse();
        try{
            request.requestCheck();
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
            PageHelper.startPage(request.getPage(),request.getSize());
            Example example = new Example(Order.class);
            example.createCriteria().andEqualTo("userId",request.getUserId());
            List<Order> orderList = orderMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(orderList)){
                response.setTotal(0L);
                response.setDetailInfoList(new ArrayList<>());
                return response;
            }
            List<OrderDetailInfo> infos = new ArrayList<>();
            PageInfo<Order> pageInfo=new PageInfo<>(orderList);
            response.setTotal(pageInfo.getTotal());
            orderList.forEach( order -> {
                OrderDetailInfo info = orderConverter.order2detail(order);
                List<OrderItem> list =  orderItemMapper.queryByOrderId(order.getOrderId());
//                OrderItemExample itemExample=new OrderItemExample();
//                itemExample.createCriteria().andOrderIdEqualTo(order.getOrderId());
//                List<OrderItem> list=orderItemMapper.selectByExample(itemExample);
                OrderShipping orderShipping=orderShippingMapper.selectByPrimaryKey(order.getOrderId());
                info.setOrderItemDto(orderConverter.item2dto(list));
                info.setOrderShippingDto(orderConverter.shipping2dto(orderShipping));
                infos.add(info);
            });
            response.setDetailInfoList(infos);
        }catch (Exception e){
            log.info("OrderQueryServiceImpl.orderList occur Exception: {}" , e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
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
//            OrderItemExample example=new OrderItemExample();
//            OrderItemExample.Criteria criteria=example.createCriteria();
//            criteria.andOrderIdEqualTo(order.getOrderId());
//            List<OrderItem> list=orderItemMapper.selectByExample(example);
            List<OrderItem> list =  orderItemMapper.queryByOrderId(order.getOrderId());
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

    @Override
    public OrderItemResponse orderItem(OrderItemRequest request) {
        OrderItemResponse response = new OrderItemResponse();
        try {
            request.requestCheck();
            OrderItem orderItem = orderItemMapper.selectByPrimaryKey(request.getOrderItemId());
            response = orderConverter.item2res(orderItem);
            Order order = orderMapper.selectByPrimaryKey(orderItem.getOrderId());
            response.setOrderDto(orderConverter.order2dto(order));
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        } catch (Exception e){
            log.error("OrderQueryServiceImpl.orderItem occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
