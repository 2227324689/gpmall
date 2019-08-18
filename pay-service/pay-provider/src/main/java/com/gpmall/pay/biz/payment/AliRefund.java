package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.commons.tool.utils.TradeNoUtils;
import com.gpmall.commons.tool.utils.UtilDate;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.Context;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.alipay.AlipayBuildRequest;
import com.gpmall.pay.biz.payment.constants.AliPaymentConfig;
import com.gpmall.pay.biz.payment.context.AliRefundContext;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentResponse;
import com.gupaoedu.pay.dto.RefundRequest;
import com.gupaoedu.pay.dto.RefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description: 支付宝退款
 * @Author： wz
 * @Date: 2019-08-17 12:53
 **/

@Slf4j
@Service("aliRefund")
public class AliRefund extends BasePayment {

	@Resource(name = "aliPaymentValidator")
	private Validator validator;

	@Autowired
	AliPaymentConfig aliPaymentConfig;


	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public Context createContext(AbstractRequest request) {
		AliRefundContext aliRefundContext = new AliRefundContext();
		RefundRequest refundRequest = (RefundRequest) request;
		aliRefundContext.setTotalFee(refundRequest.getRefundAmount());
		aliRefundContext.setOutTradeNo(refundRequest.getOrderId());
		aliRefundContext.setRefundPlatformNo(TradeNoUtils.generateTradeNo());
		return aliRefundContext;
	}

	@Override
	public void prepare(AbstractRequest request, Context context) throws BizException {
		super.prepare(request, context);
		SortedMap sParaTemp = context.getsParaTemp();
		sParaTemp.put("partner", aliPaymentConfig.getAli_partner());
		sParaTemp.put("_input_charset", aliPaymentConfig.getInput_charset());
		AliRefundContext aliRefundContext = (AliRefundContext) context;
		sParaTemp.put("service", aliPaymentConfig.getRefund_service());
		sParaTemp.put("seller_user_id", aliPaymentConfig.getSeller_id());
		sParaTemp.put("refund_date", UtilDate.getDateFormatter());
		sParaTemp.put("batch_no", aliRefundContext.getRefundPlatformNo());
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("notify_url", aliPaymentConfig.getRefund_notify_url());
		//退款详细数据，必填，格式（支付宝交易号^退款金额^备注），多笔请用#隔开
		String detail_data = aliRefundContext.getOutTradeNo() + "^" + aliRefundContext.getTotalFee().toString() +
				"^" + "正常退款";
		sParaTemp.put("detail_data", detail_data);
		aliRefundContext.setsParaTemp(sParaTemp);
	}


	@Override
	public AbstractResponse generalProcess(AbstractRequest request, Context context) throws BizException {
		Map<String, Object> sPara = AlipayBuildRequest.buildRequestParam(context.getsParaTemp(), aliPaymentConfig);
		log.info("支付宝退款组装请求参数:{}", JSON.toJSONString(context.getsParaTemp()));
		String strPara = AlipayBuildRequest.buildRequest(sPara, "get", "确认", aliPaymentConfig);
		log.info("支付宝退款同步返回结果:{}",strPara);
		RefundResponse response = new RefundResponse();
		response.setAlipayForm(strPara);
		response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
		response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
		return response;
	}

	@Override
	public void afterProcess(AbstractRequest request, AbstractResponse respond, Context context) throws BizException {

	}

	@Override
	public String getPayChannel() {
		return PayChannelEnum.ALI_REFUND.getCode();
	}

	@Override
	public AbstractResponse completePayment(AbstractRequest request) throws BizException {
		return null;
	}
}