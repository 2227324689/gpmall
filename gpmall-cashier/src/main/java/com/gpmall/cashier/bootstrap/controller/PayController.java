package com.gpmall.cashier.bootstrap.controller;/**
 * Created by mic on 2019/8/1.
 */

import com.gpmall.order.OrderCoreService;
import com.gpmall.order.dto.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午5:27
 */
@Slf4j
@RestController
public class PayController {

    @Reference
    OrderCoreService orderCoreService;

    @GetMapping("/pay")
    public void pay(){
        CreateOrderRequest request=new CreateOrderRequest();
        orderCoreService.createOrder(request);
    }
}
