package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.commons.tool.utils.TradeNoUtils;
import com.gpmall.order.OrderCoreService;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.dto.OrderItemRequest;
import com.gpmall.order.dto.OrderItemResponse;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.Context;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.wechatpay.WeChatBuildRequest;
import com.gpmall.pay.biz.payment.commons.HttpClientUtil;
import com.gpmall.pay.biz.payment.constants.WechatPaymentConfig;
import com.gpmall.pay.biz.payment.context.WechatRefundContext;
import com.gpmall.user.IAddressService;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentResponse;
import com.gupaoedu.pay.dto.RefundRequest;
import com.gupaoedu.pay.dto.RefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

	@Reference(timeout = 30000)
	private OrderQueryService orderQueryService;

	@Reference(timeout = 30000)
	private OrderCoreService orderCoreService;

	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public Context createContext(AbstractRequest request) {
		WechatRefundContext wechantRefundContext = new WechatRefundContext();
		RefundRequest refundRequest = (RefundRequest) request;
		wechantRefundContext.setOutTradeNo(refundRequest.getOrderId());
		wechantRefundContext.setRefundPlatformNo(TradeNoUtils.generateTradeNo());
		return wechantRefundContext;

	}

	@Override
	public void prepare(AbstractRequest request, Context context) throws BizException {
		super.prepare(request, context);
		SortedMap paraMap = context.getsParaTemp();
		WechatRefundContext wechatRefundContext = (WechatRefundContext) context;
		paraMap.put("appid", wechatPaymentConfig.getWechatAppid());
		paraMap.put("mch_id", wechatPaymentConfig.getWechatMch_id());
		paraMap.put("out_trade_no", wechatRefundContext.getOutTradeNo());
		paraMap.put("out_refund_no", wechatRefundContext.getRefundPlatformNo());
		paraMap.put("refund_fee", wechatRefundContext.getRefundFee().multiply(new BigDecimal("100")).intValue());
		//微信退款通知地址
		paraMap.put("notify_url", wechatPaymentConfig.getWechatNotifyurl());
		paraMap.put("nonce_str", WeChatBuildRequest.getNonceStr());
		//查找订单总金额
		OrderItemRequest orderItemRequest = new OrderItemRequest();
		orderItemRequest.setOrderItemId(wechatRefundContext.getOutTradeNo());
		OrderItemResponse orderItemResponse = orderQueryService.orderItem(orderItemRequest);
		paraMap.put("total_fee", orderItemResponse.getTotalFee().multiply(new BigDecimal("100")).intValue());
		String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
		paraMap.put("sign", sign);
		String xml = WeChatBuildRequest.getRequestXml(paraMap);
		wechatRefundContext.setXml(xml);
	}


	@Override
	public AbstractResponse generalProcess(AbstractRequest request, Context context) throws BizException {
		RefundResponse response = new RefundResponse();
		WechatRefundContext wechatRefundContext = (WechatRefundContext) context;
		log.info("微信退款组装参数：{}",wechatRefundContext.getXml());
		//wechat_refund_url 微信退款申请地址
		String xml = HttpClientUtil.httpPost(wechatPaymentConfig.getWechat_refund_url(), wechatRefundContext.getXml());
		log.info("微信退款同步返回结果:{}", xml);
		Map<String, String> resultMap = WeChatBuildRequest.doXMLParse(xml);
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
		log.info("weChatPayment begin - afterProcess -request:" + JSON.toJSONString(request) + "\n response:" + JSON.toJSONString(respond));

	}

	@Override
	public String getPayChannel() {
		return PayChannelEnum.WECHAT_REFUND.getCode();
	}

	@Override
	public AbstractResponse completePayment(AbstractRequest request) throws BizException {
		return null;
	}
}