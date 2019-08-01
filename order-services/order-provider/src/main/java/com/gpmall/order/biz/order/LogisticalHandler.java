package com.gpmall.order.biz.order;/**
 * Created by mic on 2019/8/1.
 */

import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.biz.pipeline.HandlerChain;
import com.gpmall.order.constant.OrderRetCode;
import com.gpmall.order.dal.entitys.OrderShipping;
import com.gpmall.order.dal.persistence.OrderShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午5:06
 *
 * 处理物流信息（商品寄送的地址）
 */
@Component
@Order(4)
public class LogisticalHandler extends AbstractOrderHandler{

    @Autowired
    OrderShippingMapper orderShippingMapper;

    @Override
    public void handler(OrderContext context, HandlerChain<OrderContext, BizException> handlers) throws BizException {
        try {
            OrderShipping orderShipping = new OrderShipping();
            orderShipping.setOrderId(String.valueOf(context.getOrderID()));
            orderShipping.setReceiverName(context.getRequest().getUserName());
            orderShipping.setReceiverAddress(context.getRequest().getStreetName());
            orderShipping.setReceiverPhone(context.getRequest().getTel());
            orderShipping.setCreated(new Date());
            orderShipping.setUpdated(new Date());
            orderShippingMapper.insert(orderShipping);
            handlers.handleNext(context);
        }catch (Exception e){
            throw new BizException(OrderRetCode.SHIPPING_DB_SAVED_FAILED.getCode(),OrderRetCode.SHIPPING_DB_SAVED_FAILED.getMessage());
        }
    }
}
