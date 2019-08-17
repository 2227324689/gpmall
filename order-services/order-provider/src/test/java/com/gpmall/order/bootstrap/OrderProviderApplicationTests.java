package com.gpmall.order.bootstrap;

import com.gpmall.order.dal.entitys.OrderItem;
import com.gpmall.order.dal.persistence.OrderItemMapper;
import com.gpmall.order.dal.persistence.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderProviderApplicationTests {



    @Test
    public void contextLoads() {
    }

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Test
    public void mapperTest(){
        Long aLong = orderMapper.countAll();
        System.out.println("aLong = " + aLong);
        List<OrderItem> orderItems = orderItemMapper.queryByOrderId("121");
        for (OrderItem orderItem : orderItems) {
            System.out.println("orderItem = " + orderItem);
        }
    }

}
