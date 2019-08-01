package com.gpmall.order.biz.order;/**
 * Created by mic on 2019/8/1.
 */

import com.gpmall.order.dto.CreateOrderRequest;
import com.gpmall.user.dto.QueryMemberResponse;
import lombok.Data;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午6:21
 * 上下文信息
 */
@Data
public class OrderContext {

    private CreateOrderRequest request; //请求对象

    private QueryMemberResponse memberResponse; //会员信息

    private String orderID; //订单id

    private List<Long> buyProductIds;
}
