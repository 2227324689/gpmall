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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    @PostMapping("/pay/alipayNotify")
    public String doAliPay(HttpServletRequest httpServletRequest) {
        return commonAliDo("ali_pay", httpServletRequest);
    }

    /**
     * 微信支付异步返回通知
     *
     * @return
     */
    @RequestMapping("/pay/wechatPayNotify")
    public String doWeChantPay(HttpServletRequest httpServletRequest) {
        return commonWeChatDo("wechat_pay", httpServletRequest);
    }

    /**
     * 支付宝退款异步返回
     *
     * @return
     */
    @PostMapping("/refund/aliRefundNotify")
    public String doAliRefund(HttpServletRequest httpServletRequest) {
        return commonAliDo("ali_refund", httpServletRequest);
    }

    /**
     * 微信退款异步返回
     *
     * @return
     */
    @PostMapping("/refund/wechatRefundNotify")
    public String doWechatRefund(HttpServletRequest httpServletRequest) {
        return commonWeChatDo("wechat_refund", httpServletRequest);
    }

    /**
     * 支付宝异步通知返回解析
     *
     * @param channel
     * @param httpServletRequest
     * @return
     */
    private String commonAliDo(String channel, HttpServletRequest httpServletRequest) {
        log.info("{}异步通知回调:{}", channel, JSON.toJSON(httpServletRequest.getParameterMap()));
        Map<String, String[]> map = httpServletRequest.getParameterMap();
        PaymentNotifyRequest paymentNotifyRequest = new PaymentNotifyRequest();
        paymentNotifyRequest.setPayChannel(channel);
        paymentNotifyRequest.setResultMap(map);
        PaymentNotifyResponse paymentNotifyResponse = payCoreService.paymentResultNotify(paymentNotifyRequest);
        return paymentNotifyResponse.getResult();
    }

    /**
     * 微信异步通知返回解析
     *
     * @param channel
     * @param httpServletRequest
     * @return
     */
    private String commonWeChatDo(String channel, HttpServletRequest httpServletRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        log.info("{}异步回调", channel);
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = httpServletRequest.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String a;
            while ((a = bufferedReader.readLine()) != null) {
                stringBuffer.append(a);
            }
            log.info("微信支付回调通知:{}", stringBuffer.toString());
        } catch (IOException e) {
            log.error("解析微信回调数据错误{}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (null == bufferedReader) {
                try {
                    inputStream.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("关闭流错误{}", e.getMessage());
                }
            }
        }
        PaymentNotifyRequest paymentNotifyRequest = new PaymentNotifyRequest();
        paymentNotifyRequest.setPayChannel(channel);
        paymentNotifyRequest.setXml(stringBuffer.toString());
        PaymentNotifyResponse paymentNotifyResponse = payCoreService.paymentResultNotify(paymentNotifyRequest);
        log.info("{}微信通知结果返回:{}", channel, JSON.toJSONString(paymentNotifyResponse));
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
