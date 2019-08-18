package com.gpmall.cashier.bootstrap.controller;/**
 * Created by mic on 2019/8/1.
 */

import com.gupaoedu.pay.PayCoreService;
import com.gupaoedu.pay.dto.PaymentNotifyRequest;
import com.gupaoedu.pay.dto.PaymentNotifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午5:27
 */
@Slf4j
@RestController
public class PayNotifyController {
   @Reference(timeout=3000)
   private PayCoreService payCoreService;

	@PostMapping("/pay/aliPayNotifty")
	public String doAliPay(HttpServletRequest httpServletRequest) {
		Map<String, String[]> map=httpServletRequest.getParameterMap();
		PaymentNotifyRequest paymentNotifyRequest=new PaymentNotifyRequest();
		paymentNotifyRequest.setPayChannel("ali_pay");
		//paymentNotifyRequest.setResultMap(map);
		PaymentNotifyResponse paymentNotifyResponse=payCoreService.paymentResultNotify(paymentNotifyRequest);
		return paymentNotifyResponse.getResult();
	}

	@PostMapping("/pay/wechantPayNotifty")
	public String doWechantPay() {
		return null;
	}

	@PostMapping("/refund/aliRefundNotifty")
	public String doAliRefund() {
		return null;
	}

	@PostMapping("/refund/wechatRefundNotifty")
	public String doWechatRefund() {
		return null;
	}
}
