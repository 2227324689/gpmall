package com.gpmall.order.services;

import com.alibaba.fastjson.JSON;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.constant.OrderConstants;
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
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    RedissonClient redissonClient;

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
            response.setDetailInfoList(new ArrayList<>());
            response.setCurrentPage(request.getPage());
            response.setTotalPage(0);
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
            RScoredSortedSet<Object> orderListFromRedis = redissonClient.
                    getScoredSortedSet(OrderConstants.ORDER_KEY_PREFIX + request.getUserId());
            //如果缓存存在数据 直接从缓存拿
            if(orderListFromRedis.size() > 0){
                response.setTotalPage(getPageNum(orderListFromRedis.size(),request.getSize()));
                Collection<Object> objects = orderListFromRedis.valueRangeReversed((request.getPage() - 1) * request.getSize(),
                        request.getPage() * request.getSize() - 1);
                List<OrderDetailInfo> detailInfoList = response.getDetailInfoList();
                objects.forEach( o -> {
                    detailInfoList.add(JSON.parseObject(o.toString(),OrderDetailInfo.class));
                });
                return response;
            }

            OrderExample example = new OrderExample();
            example.createCriteria().andUserIdEqualTo(request.getUserId());
            example.setOrderByClause("order_id desc");
            List<Order> orderList = orderMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(orderList)){
                return response;
            }
            List<OrderDetailInfo> detailInfos =  new ArrayList<>();
            //从db中取得数据构造结果集
            orderList.forEach( order -> {
                OrderItemExample itemExample=new OrderItemExample();
                itemExample.createCriteria().andOrderIdEqualTo(order.getOrderId());
                List<OrderItem> itemList = orderItemMapper.selectByExample(itemExample);
                OrderShipping orderShipping=orderShippingMapper.selectByPrimaryKey(order.getOrderId());
                OrderDetailInfo info = orderConverter.order2detail(order);
                info.setOrderItemDto(orderConverter.item2dto(itemList));
                info.setOrderShippingDto(orderConverter.shipping2dto(orderShipping));
                detailInfos.add(info);
            });
            RScoredSortedSet<Object> scoredSortedSet = redissonClient.
                    getScoredSortedSet(OrderConstants.ORDER_KEY_PREFIX + request.getUserId());
            scoredSortedSet.clear();
            //按照订单创建时间升序排列
            detailInfos.forEach( info -> {
                scoredSortedSet.add((double) info.getCreateTime().getTime(), JSON.toJSONString(info));
            });
            List<OrderDetailInfo> results = detailInfos.subList((request.getPage()-1) * request.getSize() ,
                    request.getPage() * request.getSize());
            response.setDetailInfoList(results);
            response.setTotalPage(getPageNum(detailInfos.size(),request.getSize()));

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

    private int getPageNum(int totalNum, int pageSize){
        if(totalNum % pageSize == 0){
            return totalNum/pageSize;
        }else{
            return totalNum/pageSize + 1;
        }
    }
}
