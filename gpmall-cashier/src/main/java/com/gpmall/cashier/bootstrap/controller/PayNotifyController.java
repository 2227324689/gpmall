package com.gpmall.cashier.bootstrap.controller;/**
 * Created by mic on 2019/8/1.
 */

import com.alibaba.fastjson.JSON;
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
	@Reference(timeout = 3000)
	private PayCoreService payCoreService;

	/**
	 * 支付宝支付结果异步通知
	 *
	 * @param httpServletRequest
	 * @return
	 */
	@PostMapping("/pay/alipayNotifty")
	public String doAliPay(HttpServletRequest httpServletRequest) {
		return commonDo("ali_pay", httpServletRequest);
	}

	/**
	 * 微信支付异步返回通知
	 *
	 * @return
	 */
	@PostMapping("/pay/wechatPayNotifty")
	public String doWeChantPay(HttpServletRequest httpServletRequest) {
		return commonDo("wechat_pay", httpServletRequest);
	}

	/**
	 * 支付宝退款异步返回
	 *
	 * @return
	 */
	@PostMapping("/refund/aliRefundNotifty")
	public String doAliRefund(HttpServletRequest httpServletRequest) {
		return commonDo("ali_refund", httpServletRequest);
	}

	/**
	 * 微信退款异步返回
	 *
	 * @return
	 */
	@PostMapping("/refund/wechatRefundNotifty")
	public String doWechatRefund(HttpServletRequest httpServletRequest) {
		return commonDo("wechat_refund", httpServletRequest);
	}

	private String commonDo(String channel, HttpServletRequest httpServletRequest) {
		log.info("{}异步通知回调:{}", channel, JSON.toJSON(httpServletRequest));
		Map<String, String[]> map = httpServletRequest.getParameterMap();
		PaymentNotifyRequest paymentNotifyRequest = new PaymentNotifyRequest();
		paymentNotifyRequest.setPayChannel(channel);
		paymentNotifyRequest.setResultMap(map);
		PaymentNotifyResponse paymentNotifyResponse = payCoreService.paymentResultNotify(paymentNotifyRequest);
		return paymentNotifyResponse.getResult();
	}
}
