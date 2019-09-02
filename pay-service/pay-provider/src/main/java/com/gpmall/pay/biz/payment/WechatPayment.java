package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.commons.tool.utils.UtilDate;
import com.gpmall.order.OrderCoreService;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.Context;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.wechatpay.WeChatBuildRequest;
import com.gpmall.pay.biz.payment.commons.HttpClientUtil;
import com.gpmall.pay.biz.payment.constants.PayResultEnum;
import com.gpmall.pay.dal.entitys.Payment;
import com.gpmall.pay.dal.persistence.PaymentMapper;
import com.gpmall.pay.utils.GlobalIdGeneratorUtil;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gpmall.pay.biz.payment.constants.PaymentConstants;
import com.gpmall.pay.biz.payment.constants.WechatPaymentConfig;
import com.gpmall.pay.biz.payment.context.WechatPaymentContext;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 *
 * @author 风骚的Michael 老师
 */
@Service("wechatPayment")
public class WechatPayment extends BasePayment<PaymentResponse,PaymentRequest,WechatPaymentContext> {

	@Autowired
	private WechatPaymentConfig wechatPaymentConfig;

	@Resource(name = "wechatPaymentValidator")
	private Validator validator;

	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private GlobalIdGeneratorUtil globalIdGeneratorUtil;

	private final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_ID";

	@Reference(timeout = 3000)
	OrderCoreService orderCoreService;

	@Override
	public Validator getValidator() {
		return validator;
	}

	@Override
	public WechatPaymentContext createContext(PaymentRequest request) {
		WechatPaymentContext wechatPaymentContext = new WechatPaymentContext();
		wechatPaymentContext.setOutTradeNo(request.getTradeNo());
		wechatPaymentContext.setProductId(request.getTradeNo());
		wechatPaymentContext.setSpbillCreateIp(request.getSpbillCreateIp());
		wechatPaymentContext.setTradeType(PaymentConstants.TradeTypeEnum.NATIVE.getType());
		wechatPaymentContext.setTotalFee(request.getTotalFee());
		wechatPaymentContext.setBody(request.getSubject());
		return wechatPaymentContext;
	}

	@Override
	public void prepare(PaymentRequest request, WechatPaymentContext context) throws BizException {
		super.prepare(request, context);
		SortedMap paraMap = context.getsParaTemp();
		paraMap.put("body", context.getBody());
		paraMap.put("out_trade_no", context.getOutTradeNo());
		//单位分
		paraMap.put("total_fee", context.getTotalFee().multiply(new BigDecimal("100")).intValue());
		paraMap.put("spbill_create_ip", context.getSpbillCreateIp());
		paraMap.put("appid", wechatPaymentConfig.getWechatAppid());
		paraMap.put("mch_id", wechatPaymentConfig.getWechatMch_id());
		paraMap.put("nonce_str", WeChatBuildRequest.getNonceStr());
		paraMap.put("trade_type", context.getTradeType());
		paraMap.put("product_id", context.getProductId());
		// 此路径是微信服务器调用支付结果通知路径
		paraMap.put("device_info", "WEB");
		paraMap.put("notify_url", wechatPaymentConfig.getWechatNotifyurl());
		//二维码的失效时间（5分钟）
		paraMap.put("time_expire", UtilDate.getExpireTime(30 * 60 * 1000L));
		String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
		paraMap.put("sign", sign);
		log.info("微信生成sign:{}", JSON.toJSONString(paraMap));
		String xml = WeChatBuildRequest.getRequestXml(paraMap);
		context.setXml(xml);
	}

	@Override
	public PaymentResponse generalProcess(PaymentRequest request, WechatPaymentContext context) throws BizException {
		PaymentResponse response = new PaymentResponse();
		log.info("微信支付组装的请求参数:{}", context.getXml());
		String xml = HttpClientUtil.httpPost(wechatPaymentConfig.getWechatUnifiedOrder(), context.getXml());
		log.info("微信支付同步返回的结果:{}", xml);
		Map<String, String> resultMap = WeChatBuildRequest.doXMLParse(xml);
		if ("SUCCESS".equals(resultMap.get("return_code"))) {
			if ("SUCCESS".equals(resultMap.get("result_code"))) {
				response.setPrepayId(resultMap.get("prepay_id"));
				response.setCodeUrl(resultMap.get("code_url"));
				response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
				response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
			} else {
				String errMsg = resultMap.get("err_code") + ":" + resultMap.get("err_code_des");
				response.setCode(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getCode());
				response.setMsg(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getMsg(errMsg));
			}
		} else {
			response.setCode(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getCode());
			response.setMsg(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getMsg(resultMap.get("return_msg")));
		}
		return response;
	}

	@Override
	public void afterProcess(PaymentRequest request, PaymentResponse respond, WechatPaymentContext context) throws BizException {
		//插入支付表
		log.info("Alipayment begin - afterProcess -request:{},\n response:{}",
				JSON.toJSONString(request) , JSONObject.toJSONString(respond));
		//插入支付记录表
		Payment payment = new Payment();
		payment.setCreateTime(new Date());
		BigDecimal amount = request.getOrderFee();
		payment.setOrderAmount(amount);
		payment.setOrderId(request.getTradeNo());
		//payment.setTradeNo(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
		payment.setPayerAmount(amount);
		payment.setPayerUid(request.getUserId());
		//TODO
		payment.setPayerName("");
		payment.setPayWay(request.getPayChannel());
		payment.setProductName(request.getSubject());
		payment.setStatus(PayResultEnum.TRADE_PROCESSING.getCode());//
		payment.setRemark("微信支付");
		//第三方的交易id
		payment.setPayNo(respond.getPrepayId());
		payment.setUpdateTime(new Date());
		paymentMapper.insert(payment);
	}

	@Override
	public String getPayChannel() {
		return PayChannelEnum.WECHAT_PAY.getCode();
	}

	@Override
	public PaymentNotifyResponse completePayment(PaymentNotifyRequest request) throws BizException {
		request.requestCheck();
		PaymentNotifyResponse response = new PaymentNotifyResponse();
		Map xmlMap = new HashMap();
		String xml = request.getXml();
		try {
			xmlMap = WXPayUtil.xmlToMap(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SortedMap<Object, Object> paraMap = new TreeMap<>();
		xmlMap.forEach(paraMap::put);
		//组装返回的结果的签名字符串
		String rsSign = paraMap.remove("sign").toString();
		String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
		//验证签名
		if (rsSign.equals(sign)) {
			//SUCCESS、FAIL
			String resultCode = paraMap.get("return_code").toString();
			if ("SUCCESS".equals(resultCode)) {
				if ("SUCCESS".equals(paraMap.get("result_code"))) {
					//更新支付表
					Payment payment = new Payment();
					payment.setStatus(PayResultEnum.TRADE_SUCCESS.getCode());
					payment.setPaySuccessTime((UtilDate.parseStrToDate(UtilDate.simple,paraMap.get("time_end").toString(),new Date())));
					Example example = new Example(Payment.class);
					example.createCriteria().andEqualTo("orderId", paraMap.get("out_trade_no"));
					paymentMapper.updateByExampleSelective(payment, example);
					//更新订单表状态
					orderCoreService.updateOrder(1, paraMap.get("out_trade_no").toString());
					response.setResult(WeChatBuildRequest.setXML("SUCCESS", "OK"));
				}
			}
		} else {
			throw new BizException("微信返回结果签名验证失败");
		}
		return response;
	}
}
