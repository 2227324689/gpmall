package com.gpmall.order.converter;/**
 * Created by mic on 2019/7/31.
 */

import com.gpmall.order.dal.entitys.Order;
import com.gpmall.order.dal.entitys.OrderItem;
import com.gpmall.order.dal.entitys.OrderShipping;
import com.gpmall.order.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/31-上午9:57
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({})
    OrderDetailResponse order2res(Order order);

    @Mappings({})
    OrderDetailInfo order2detail(Order order);

    @Mappings({})
    OrderItemDto item2dto(OrderItem item);

    @Mappings({})
    OrderShippingDto shipping2dto(OrderShipping shipping);


    List<OrderItemDto> item2dto(List<OrderItem> items);

    @Mappings({})
    OrderItemResponse item2res(OrderItem orderItem);

    @Mappings({})
    OrderDto order2dto(Order order);
}
