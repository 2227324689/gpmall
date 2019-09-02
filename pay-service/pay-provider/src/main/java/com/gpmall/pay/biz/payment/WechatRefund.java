package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.OrderCoreService;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.dto.OrderItemRequest;
import com.gpmall.order.dto.OrderItemResponse;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.Context;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.wechatpay.AESUtil;
import com.gpmall.pay.biz.payment.channel.wechatpay.WeChatBuildRequest;
import com.gpmall.pay.biz.payment.commons.HttpClientUtil;
import com.gpmall.pay.biz.payment.constants.WechatPaymentConfig;
import com.gpmall.pay.biz.payment.context.WechatRefundContext;
import com.gpmall.pay.dal.entitys.Refund;
import com.gpmall.pay.dal.persistence.RefundMapper;
import com.gpmall.pay.utils.GlobalIdGeneratorUtil;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.constants.RefundCodeEnum;
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
 * @Description: 微信退款
 * @Author： wz
 * @Date: 2019-08-17 12:54
 **/
@Slf4j
@Service("wechatRefund")
public class WechatRefund extends BasePayment {
	@Autowired
	private WechatPaymentConfig wechatPaymentConfig;

	@Resource(name = "wechatPaymentValidator")
	private Validator validator;

	@Reference(timeout = 3000)
	OrderQueryService orderQueryService;
	@Reference(timeout = 3000)
	OrderCoreService orderCoreService;
	@Autowired
	RefundMapper refundMapper;

	@Autowired
	GlobalIdGeneratorUtil globalIdGeneratorUtil;

	private final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_ID";

	@Override
	public Validator getValidator() {
		return validator;
	}

	@Override
	public Context createContext(AbstractRequest request) {
		WechatRefundContext wechantRefundContext = new WechatRefundContext();
		RefundRequest refundRequest = (RefundRequest) request;
		wechantRefundContext.setOutTradeNo(refundRequest.getOrderId());
		wechantRefundContext.setRefundPlatformNo(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
		wechantRefundContext.setRefundFee(refundRequest.getRefundAmount());
		return wechantRefundContext;

	}

	@Override
	public void prepare(AbstractRequest request, Context context) throws BizException {
		super.prepare(request, context);
		SortedMap paraMap = context.getsParaTemp();
		WechatRefundContext wechatRefundContext = (WechatRefundContext) context;
		paraMap.put("out_trade_no", wechatRefundContext.getOutTradeNo());
		paraMap.put("out_refund_no", wechatRefundContext.getRefundPlatformNo());
		paraMap.put("refund_fee", wechatRefundContext.getRefundFee().multiply(new BigDecimal("100")).setScale(0).toString());
		//微信退款通知地址
		paraMap.put("notify_url", wechatPaymentConfig.getWechat_refund_notify_url());
		paraMap.put("nonce_str", WeChatBuildRequest.getNonceStr());
		//查找订单总金额
		OrderItemRequest orderItemRequest = new OrderItemRequest();
		orderItemRequest.setOrderItemId(wechatRefundContext.getOutTradeNo());
		//OrderItemResponse orderItemResponse = orderQueryService.orderItem(orderItemRequest);
		paraMap.put("total_fee", "1");
	}


	@Override
	public AbstractResponse generalProcess(AbstractRequest request, Context context) throws BizException {
		RefundResponse response = new RefundResponse();
		WechatRefundContext wechatRefundContext = (WechatRefundContext) context;
		Map map = wechatRefundContext.getsParaTemp();
		Map<String, String> resultMap = new HashMap<String, String>();
		WXPay wxPay = null;
		try {
			wxPay = new WXPay(wechatPaymentConfig);
			resultMap = wxPay.refund(JSONObject.parseObject(JSON.toJSONString(map), Map.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("微信退款同步返回结果:{}", JSON.toJSONString(resultMap));
		if ("SUCCESS".equals(resultMap.get("return_code"))) {
			if ("SUCCESS".equals(resultMap.get("result_code"))) {
				response.setRefundAmount(new BigDecimal(resultMap.get("refund_fee")).divide(new BigDecimal("100")));
				response.setRefundNo(resultMap.get("refund_id"));
				response.setRefundPlatformNo(resultMap.get("out_refund_no"));
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
	public void afterProcess(AbstractRequest request, AbstractResponse respond, Context context) throws BizException {
		log.info("weChatRefund begin - afterProcess -request:" + JSON.toJSONString(request) + "\n response:" + JSON.toJSONString(respond));
		WechatRefundContext wechatRefundContext = (WechatRefundContext) context;
		RefundResponse refundResponse = (RefundResponse) respond;
		//写入退款记录表
		Refund refund = new Refund();
		refund.setOrderId(wechatRefundContext.getOutTradeNo());
		refund.setAmount(refundResponse.getRefundAmount());
		refund.setChannel(2);
		refund.setStatus(0);
		refund.setTradeNo(wechatRefundContext.getRefundPlatformNo());
		refund.setUserId(wechatRefundContext.getUserId());
		refund.setUserName("");
		refundMapper.insert(refund);
	}

	@Override
	public String getPayChannel() {
		return PayChannelEnum.WECHAT_REFUND.getCode();
	}

	@Override
	public AbstractResponse completePayment(PaymentNotifyRequest request) throws BizException {
		request.requestCheck();
		PaymentNotifyResponse response = new PaymentNotifyResponse();
		Map<String, String> xmlMap = new HashMap();
		String xml = request.getXml();
		try {
			xmlMap = WXPayUtil.xmlToMap(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String resultCode = xmlMap.get("return_code");
		if (!"SUCCESS".equals(resultCode)) {
			String msg = (String) xmlMap.get("return_msg");
			log.error("回调状态错误{}", msg);
			throw new BizException("1066666", "微信退款回调状态不对" + msg);
		}

		String req_info = xmlMap.get("req_info");
		//对加密字段进行解密，规则如下
		/*
		 *解密步骤如下：
		 *（1）对加密串A做base64解码，得到加密串B
		 *（2）对商户key做md5，得到32位小写key* ( key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 )
		 *（3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
		 */
		String decode = null;
		Map<String, String> result = null;
		try {
			decode = AESUtil.decryptData(req_info, wechatPaymentConfig.getKey());
			result = WXPayUtil.xmlToMap(decode);
			log.info("解密结果,{}", JSON.toJSONString(result));
		} catch (Exception e) {
			log.error("解密失败{}", e.getMessage());
			throw new BizException(RefundCodeEnum.REFUND_NOTIFY_PASRE_FAIL.getCode(), RefundCodeEnum.REFUND_NOTIFY_PASRE_FAIL.getMsg());
		}
		String tradeNo = result.get("out_trade_no"); //商户订单号
		String refundId = result.get("refund_id");   //微信退款单号
		String outRefundNo = result.get("out_refund_no");    //商户退款单号
		String settlementRefundFee = result.get("settlement_refund_fee");  //退款金额
		String refundStatus = result.get("refund_status");  //退款状态

		//写入退款记录表
		Refund refund = new Refund();
		refund.setRefundNo(refundId);
		refund.setOrderId(tradeNo);
		refund.setAmount(new BigDecimal(settlementRefundFee));
		int status = refundStatus.equals("SUCCESS") ? 1 : 2;
		refund.setStatus(status);
		Example example = new Example(Refund.class);
		example.createCriteria().andEqualTo("tradeNo", outRefundNo);
		refundMapper.updateByExampleSelective(refund, example);
		//更新订单状态为退款状态
		if (status == 1) {
			orderCoreService.updateOrder(7, outRefundNo);
		}
		response.setResult(WeChatBuildRequest.setXML("SUCCESS", "OK"));
		return response;

	}
}
