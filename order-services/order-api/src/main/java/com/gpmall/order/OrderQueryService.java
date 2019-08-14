package com.gpmall.order;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.order.dto.*;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午10:01
 */
public interface OrderQueryService {


    /**
     * 查询指定用户下订单总数
     * @param request
     * @return
     */
    OrderCountResponse orderCount(OrderCountRequest request);


    /**
     * 查询当前用户的历史订单列表
     * @param request
     * @return
     */
    OrderListResponse orderList(OrderListRequest request);


    /**
     * 查询订单明细
     * @param request
     * @return
     */
    OrderDetailResponse orderDetail(OrderDetailRequest request);

    /**
     * 查询订单条目
     * @param request
     * @return
     */
    OrderItemResponse orderItem(OrderItemRequest request);

}
