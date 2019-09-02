package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.OrderCoreService;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.alipay.AlipayBuildRequest;
import com.gpmall.pay.biz.payment.channel.alipay.AlipayNotify;
import com.gpmall.pay.biz.payment.constants.AliPaymentConfig;
import com.gpmall.pay.biz.payment.constants.PayResultEnum;
import com.gpmall.pay.biz.payment.context.AliPaymentContext;
import com.gpmall.pay.dal.entitys.Payment;
import com.gpmall.pay.dal.persistence.PaymentMapper;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentNotifyRequest;
import com.gupaoedu.pay.dto.PaymentNotifyResponse;
import com.gupaoedu.pay.dto.PaymentRequest;
import com.gupaoedu.pay.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Service("aliPayment")
public class AliPayment extends BasePayment<PaymentResponse,PaymentRequest,AliPaymentContext> {
	@Resource(name = "aliPaymentValidator")
	private Validator validator;

	@Autowired
	private AliPaymentConfig aliPaymentConfig;

	@Autowired
	private PaymentMapper paymentMapper;

	@Reference(timeout = 30000)
	private OrderCoreService orderCoreService;

	@Override
	public Validator getValidator() {
		return validator;
	}

	@Override
	public AliPaymentContext createContext(PaymentRequest request) {
		AliPaymentContext aliPaymentContext = new AliPaymentContext();
		PaymentRequest paymentRequest = (PaymentRequest) request;
		aliPaymentContext.setSubject(paymentRequest.getSubject());
		aliPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
		aliPaymentContext.setTotalFee(paymentRequest.getTotalFee());
		aliPaymentContext.setUserId(paymentRequest.getUserId());
		return aliPaymentContext;
	}

	@Override
	public PaymentResponse generalProcess(PaymentRequest request, AliPaymentContext context) throws BizException {
		Map<String, Object> sPara = AlipayBuildRequest.buildRequestParam(context.getsParaTemp(), aliPaymentConfig);
		log.info("支付宝支付组装请求参数:{}", JSON.toJSONString(sPara));
		String strPara = AlipayBuildRequest.buildRequest(sPara, "get", "确认", aliPaymentConfig);
		log.info("支付宝支付同步返回的表单:{}",strPara);
		PaymentResponse response = new PaymentResponse();
		response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
		response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
		response.setHtmlStr(strPara);
		return response;
	}

	@Override
	public void afterProcess(PaymentRequest request, PaymentResponse respond, AliPaymentContext context) throws BizException {
		log.info("Alipayment begin - afterProcess -request:" + request + "\n response:" + respond);
		PaymentRequest paymentRequest = (PaymentRequest) request;
		//插入支付记录表
		Payment payment = new Payment();
		payment.setCreateTime(new Date());
		//订单号
		payment.setOrderId(paymentRequest.getTradeNo());
		payment.setCreateTime(new Date());
		BigDecimal amount =paymentRequest.getOrderFee();
		payment.setOrderAmount(amount);
		payment.setPayerAmount(amount);
		payment.setPayerUid(paymentRequest.getUserId());
		payment.setPayerName("");//TODO
		payment.setPayWay(paymentRequest.getPayChannel());
		payment.setProductName(paymentRequest.getSubject());
		payment.setStatus(PayResultEnum.TRADE_PROCESSING.getCode());
		payment.setRemark("支付宝支付");
		payment.setUpdateTime(new Date());
		paymentMapper.insert(payment);
	}

	@Override
	public String getPayChannel() {
		return PayChannelEnum.ALI_PAY.getCode();
	}

	/**
	 * 回调通知处理
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@Override
	@Transactional
	public PaymentNotifyResponse completePayment(PaymentNotifyRequest request) throws BizException {
		request.requestCheck();
		Map requestParams = request.getResultMap();
		Map<String, Object> params = new HashMap<>(requestParams.size());
		requestParams.forEach((key, value) -> {
			String[] values = (String[]) value;
			params.put((String) key, StringUtils.join(values, ","));
		});

		PaymentNotifyResponse response = new PaymentNotifyResponse();
		String orderId = params.get("out_trade_no").toString();
		//验证
		if (AlipayNotify.verify(params, aliPaymentConfig)) {
			com.gpmall.pay.dal.entitys.Payment payment = new Payment();
			//TRADE_FINISH(支付完成)、TRADE_SUCCESS(支付成功)、FAIL(支付失败)
			String tradeStatus = params.get("trade_status").toString();
			if ("TRADE_SUCCESS".equals(tradeStatus)) {
				//更新支付表
				payment.setPayNo(params.get("trade_no").toString());
				payment.setStatus(PayResultEnum.TRADE_SUCCESS.getCode());
				payment.setPaySuccessTime((Date) params.get("gmt_payment"));
				Example example=new Example(Payment.class);
				example.createCriteria().andEqualTo("orderId",orderId);
				paymentMapper.updateByExampleSelective(payment,example);
				//更新订单表状态
				orderCoreService.updateOrder(1, orderId);
				response.setResult("success");
				return response;
			} else if ("TRADE_FINISH".equals(tradeStatus)) {
				payment.setStatus(PayResultEnum.TRADE_FINISHED.getCode());
				paymentMapper.updateByExampleSelective(payment, orderId);
				//更新订单表状态
				orderCoreService.updateOrder(1, orderId);
				response.setResult("success");
			} else if ("FAIL".equals(tradeStatus)) {
				payment.setStatus(PayResultEnum.FAIL.getCode());
				paymentMapper.updateByExampleSelective(payment, orderId);
				response.setResult("success");
			} else {
				response.setResult("fail");
			}
		} else {
			throw new BizException("支付宝支付验签失败");
		}
		return response;
	}
}
