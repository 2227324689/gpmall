package com.gpmall.order.dal.persistence;

import com.gpmall.commons.tool.tkmapper.TkMapper;
import com.gpmall.order.dal.entitys.Order;

public interface OrderMapper extends TkMapper<Order> {
    Long countAll();
}