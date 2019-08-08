package com.gpmall.shopping.controller;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.order.OrderCoreService;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.constant.OrderRetCode;
import com.gpmall.order.dto.CreateOrderRequest;
import com.gpmall.order.dto.CreateOrderResponse;
import com.gpmall.shopping.form.PageResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午9:26
 */
@RestController
@RequestMapping("/shopping")
public class OrderController {

    @Reference(timeout = 3000)
    OrderCoreService orderCoreService;

    /*@Reference
    OrderQueryService orderQueryService;*/
    /**
     * 创建订单
     */
    @PostMapping("/order")
    public ResponseData order(@RequestBody CreateOrderRequest request){
        CreateOrderResponse response=orderCoreService.createOrder(request);
        if(response.getCode().equals(OrderRetCode.SUCCESS.getCode())){
            return new ResponseUtil().setData(response.getOrderId());
        }
        return new ResponseUtil<>().setErrorMsg(response.getMsg());
    }

    /**
     * 获取当前用户的所有订单
     * @return
     */
    @GetMapping("/order")
    public ResponseData orderByCurrentId(){
        return new ResponseUtil<>().setData(null);
    }

    /**
     * 查询订单详情
     * @return
     */
    @GetMapping("/order/{id}")
    public ResponseData orderDetail(@PathVariable String id){
        return new ResponseUtil<>().setData(null);
    }

    /**
     * 取消订单
     * @return
     */
    @PutMapping("/order")
    public ResponseData orderCancel(){
        return new ResponseUtil<>().setData(null);
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/order/{id}")
    public ResponseData orderDel(@PathVariable String id){
        return new ResponseUtil<>().setData(null);
    }

}
