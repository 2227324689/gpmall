package com.gpmall.cashier.bootstrap.controller;/**
 * Created by mic on 2019/8/1.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gpmall.cashier.bootstrap.form.PayForm;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.user.intercepter.TokenIntercepter;
import com.gupaoedu.pay.PayCoreService;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentRequest;
import com.gupaoedu.pay.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午5:27
 */
@Slf4j
@RestController
@RequestMapping("/cashier")
public class PayController {

    @Reference(timeout = 3000)
    PayCoreService payCoreService;

    @PostMapping("/pay")
    public ResponseData pay(@RequestBody PayForm payForm, HttpServletRequest httpServletRequest){
        PaymentRequest request=new PaymentRequest();
        String userInfo=(String)httpServletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object= JSON.parseObject(userInfo);
        Long uid=Long.parseLong(object.get("uid").toString());
        request.setUserId(uid);
        BigDecimal money=payForm.getMoney();
        money=money.multiply(new BigDecimal(100));
        request.setOrderFee(money.intValue());
        request.setPayChannel(payForm.getPayType());
        request.setSubject(payForm.getInfo());
        request.setTradeNo(payForm.getOrderId());
        request.setTotalFee(money.intValue());
        PaymentResponse response=payCoreService.execPay(request);
        if(response.getCode().equals(PayReturnCodeEnum.SUCCESS.getCode())){
            return new ResponseUtil<>().setData(response.getHtmlStr());
        }
        return new ResponseUtil<>().setErrorMsg(response.getMsg());

    }

    public static void main(String[] args) {
        BigDecimal bigDecimal=new BigDecimal(23.33);

        bigDecimal=bigDecimal.multiply(new BigDecimal(100));

    }
}
