package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/1.
 */

import com.gpmall.order.biz.context.TransHandlerContext;
import org.springframework.stereotype.Component;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午5:06
 *
 * 处理物流信息（商品寄送的地址）
 */
@Component
public class LogisticalHandler extends AbstractTransHandler {
    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        return false;
    }

    /*  @Autowired
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
    }*/
}
