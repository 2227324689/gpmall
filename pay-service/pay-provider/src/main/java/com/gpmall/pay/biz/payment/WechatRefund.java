package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
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
import com.gpmall.pay.biz.payment.channel.wechatpay.WeChatBuildRequest;
import com.gpmall.pay.biz.payment.commons.HttpClientUtil;
import com.gpmall.pay.biz.payment.constants.WechatPaymentConfig;
import com.gpmall.pay.biz.payment.context.WechatRefundContext;
import com.gpmall.pay.dal.entitys.Refund;
import com.gpmall.pay.dal.persistence.RefundMapper;
import com.gpmall.pay.utils.GlobalIdGeneratorUtil;
import com.gupaoedu.pay.constants.PayChannelEnum;
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
		return null;
	}

	@Override
	public Context createContext(AbstractRequest request) {
		WechatRefundContext wechantRefundContext = new WechatRefundContext();
		RefundRequest refundRequest = (RefundRequest) request;
		wechantRefundContext.setOutTradeNo(refundRequest.getOrderId());
		wechantRefundContext.setRefundPlatformNo(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
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
		paraMap.put("notify_url", wechatPaymentConfig.getWechat_refund_notify_url());
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
		log.info("微信退款组装参数：{}", wechatRefundContext.getXml());
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
		log.info("weChatRefund begin - afterProcess -request:" + JSON.toJSONString(request) + "\n response:" + JSON.toJSONString(respond));
		WechatRefundContext wechatRefundContext = (WechatRefundContext) context;
		RefundResponse refundResponse = (RefundResponse) respond;
		//写入退款记录表
		Refund refund = new Refund();
		refund.setOrderId(wechatRefundContext.getOutTradeNo());
		refund.setAmount(refundResponse.getRefundAmount());
		refund.setChannel("wechant_refund");
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
		PaymentNotifyResponse response = new PaymentNotifyResponse();
		SortedMap<Object, Object> paraMap = new TreeMap<>();
		Map<String, String[]> resultMap = request.getResultMap();
		for (String s : resultMap.keySet()) {
			String value = Arrays.toString(resultMap.get(s));
			paraMap.put(s, value);
		}
		//组装返回的结果的签名字符串
		String rsSign = resultMap.remove("sign").toString();
		String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
		//验证签名
		if (rsSign.equals(sign)) {
			//SUCCESS、FAIL
			String resultCode = paraMap.get("return_code").toString();
			if ("SUCCESS".equals(resultCode)) {
				if ("SUCCESS".equals(paraMap.get("result_code"))) {
					//写入退款记录表
					Refund refund = new Refund();
					String amount = (String) paraMap.get("refund_fee");
					String refundNo = (String) paraMap.get("refund_id");
					refund.setRefundNo(refundNo);
					refund.setAmount(new BigDecimal(amount));
					int status = paraMap.get("refund_status").equals("SUCCESS") ? 1 : 2;
					refund.setStatus(status);
					Example example = new Example(Refund.class);
					example.createCriteria().andEqualTo("tradeNo", paraMap.get("out_refund_no"));
					refundMapper.updateByExampleSelective(refund, example);
					//更新订单状态为退款状态
					if (status == 1) {
						orderCoreService.updateOrder(7, paraMap.get("out_trade_no").toString());
					}
					response.setResult(WeChatBuildRequest.setXML("SUCCESS", "OK"));
				}
			}
		} else {
			throw new BizException("支付宝退款验签失败");
		}
		return response;
	}
}
