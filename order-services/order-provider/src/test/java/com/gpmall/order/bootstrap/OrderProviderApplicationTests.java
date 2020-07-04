package com.gpmall.order.bootstrap;

import com.gpmall.order.OrderCoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderProviderApplication.class)
public class OrderProviderApplicationTests {
    @Autowired
    private OrderCoreService orderCoreService;
    @Test
    public void contextLoads() {
        orderCoreService.updateOrderCancel("20011411470626160");
    }

}
