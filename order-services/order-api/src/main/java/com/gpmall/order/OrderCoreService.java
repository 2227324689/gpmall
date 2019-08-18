package com.gpmall.order;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.order.dto.*;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午9:13
 * 订单相关业务
 */
public interface OrderCoreService {

    /**
     * 创建订单
     * @param request
     * @return
     */
    CreateOrderResponse createOrder(CreateOrderRequest request);

    /**
     * 取消订单
     * @param request
     * @return
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest request);


    /**
     * 删除订单
     * @param request
     * @return
     */
    DeleteOrderResponse deleteOrder(DeleteOrderRequest request);


    void updateOrder(Integer status,String orderId);

    /**
     * 删除订单与交易
     * @param request
     */
    void deleteOrderWithTransaction(DeleteOrderRequest request);


}
