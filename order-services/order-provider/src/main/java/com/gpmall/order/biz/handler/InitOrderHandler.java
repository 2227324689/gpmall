package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/1.
 */

import com.gpmall.order.biz.callback.SendEmailCallback;
import com.gpmall.order.biz.callback.TransCallback;
import com.gpmall.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午5:01
 * 初始化订单
 */

@Slf4j
@Component
public class InitOrderHandler extends AbstractTransHandler {
    @Autowired
    SendEmailCallback sendEmailCallback;

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        return false;
    }

    /*@Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    GlobalIdGeneratorUtil globalIdGeneratorUtil;

    private final String ORDER_GLOBAL_ID_CACHE_KEY="ORDER_ID";
    private final String ORDER_ITEM_GLOBAL_ID_CACHE_KEY="ORDER_ITEM_ID";

    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor = BizException.class)
    @Override
    public void handler(OrderContext context, HandlerChain<OrderContext, BizException> handlers) throws BizException {
        com.gpmall.order.dal.entitys.Order order=new com.gpmall.order.dal.entitys.Order();
        try {
            String orderId = globalIdGeneratorUtil.getNextSeq(ORDER_GLOBAL_ID_CACHE_KEY, 1);
            order.setOrderId(orderId);
            order.setUserId(Long.valueOf(context.getRequest().getUserId()));
            order.setBuyerNick(context.getMemberResponse().getUsername());
            order.setPayment(context.getRequest().getOrderTotal());
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            order.setStatus(OrderConstants.ORDER_STATUS_INIT);
            orderMapper.insert(order); //保存订单
            List<Long> buyProductIds=new ArrayList<>();
            context.getRequest().getCartProductDtoList().parallelStream().forEach(cartProductDto -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(globalIdGeneratorUtil.getNextSeq(ORDER_ITEM_GLOBAL_ID_CACHE_KEY, 1));
                orderItem.setItemId(String.valueOf(cartProductDto.getProductId()));
                orderItem.setOrderId(String.valueOf(orderId));
                orderItem.setNum(Math.toIntExact(cartProductDto.getProductNum()));
                orderItem.setPrice(cartProductDto.getSalePrice());
                orderItem.setTitle(cartProductDto.getProductName());
                orderItem.setPicPath(cartProductDto.getProductImg());
                orderItem.setTotalFee(cartProductDto.getSalePrice().multiply(BigDecimal.valueOf(cartProductDto.getProductNum())));
                buyProductIds.add(cartProductDto.getProductId());
                orderItemMapper.insert(orderItem);
            });
            context.setOrderID(orderId);
            context.setBuyProductIds(buyProductIds);
            handlers.handleNext(context);
        }catch (Exception e){
            log.error("InitOrderHandler occur Exception :"+e);
            throw new BizException(OrderRetCode.DB_SAVE_EXCEPTION.getCode(),OrderRetCode.DB_SAVE_EXCEPTION.getMessage());
        }
    }*/

    @Override
    public TransCallback setPostCallback() {
        return sendEmailCallback;
    }
}
