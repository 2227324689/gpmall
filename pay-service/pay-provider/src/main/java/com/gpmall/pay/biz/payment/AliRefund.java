package com.gpmall.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.commons.tool.utils.UtilDate;
import com.gpmall.order.OrderCoreService;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.Context;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.alipay.AlipayBuildRequest;
import com.gpmall.pay.biz.payment.channel.alipay.AlipayNotify;
import com.gpmall.pay.biz.payment.constants.AliPaymentConfig;
import com.gpmall.pay.biz.payment.context.AliRefundContext;
import com.gpmall.pay.dal.entitys.Refund;
import com.gpmall.pay.dal.persistence.PaymentMapper;
import com.gpmall.pay.dal.persistence.RefundMapper;
import com.gpmall.pay.utils.GlobalIdGeneratorUtil;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

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
	@Autowired
	RefundMapper refundMapper;
	@Autowired
	PaymentMapper paymentMapper;

	@Reference(timeout = 3000)
	OrderCoreService orderCoreService;

	@Autowired
	GlobalIdGeneratorUtil globalIdGeneratorUtil;

	private final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_ID";

	@Override
	public Validator getValidator() {
		return validator;
	}

	@Override
	public Context createContext(AbstractRequest request) {
		AliRefundContext aliRefundContext = new AliRefundContext();
		RefundRequest refundRequest = (RefundRequest) request;
		aliRefundContext.setTotalFee(refundRequest.getRefundAmount());
		aliRefundContext.setOutTradeNo(refundRequest.getOrderId());
		aliRefundContext.setRefundPlatformNo(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY,1));
		aliRefundContext.setUserId(refundRequest.getUserId());
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
		log.info("支付宝退款同步返回结果:{}", strPara);
		RefundResponse response = new RefundResponse();
		response.setAlipayForm(strPara);
		response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
		response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
		return response;
	}

	@Override
	public void afterProcess(AbstractRequest request, AbstractResponse respond, Context context) throws BizException {
		AliRefundContext aliRefundContext = (AliRefundContext) context;
		//写入退款记录表
		Refund refund = new Refund();
		refund.setOrderId(aliRefundContext.getOutTradeNo());
		refund.setAmount(aliRefundContext.getTotalFee());
		refund.setChannel(1);
		refund.setStatus(0);
		refund.setTradeNo(aliRefundContext.getRefundPlatformNo());
		refund.setUserId(aliRefundContext.getUserId());
		refund.setUserName("");
		refundMapper.insert(refund);
	}

	@Override
	public String getPayChannel() {
		return PayChannelEnum.ALI_REFUND.getCode();
	}

	@Override
	public AbstractResponse completePayment(PaymentNotifyRequest request) throws BizException {
		request.requestCheck();
		Map requestParams = request.getResultMap();
		Map<String, Object> params = new HashMap<>(requestParams.size());
		requestParams.forEach((key, value) -> {
			String[] values = (String[]) value;
			params.put((String) key, StringUtils.join(values, ","));
		});
		if (AlipayNotify.verify(params, aliPaymentConfig)) {
			//写入退款记录表
			Refund refund = new Refund();
			String details = (String) params.get("result_details");
			String[] strings = details.split("^");
			refund.setRefundNo(strings[0]);
			int status = "SUCCESS".equals(strings[2]) ? 1 : 2;
			refund.setStatus(status);
			Example example = new Example(Refund.class);
			example.createCriteria().andEqualTo("tradeNo", params.get("batch_no"));
			refundMapper.updateByExampleSelective(refund, example);
			Refund refund1 = refundMapper.selectByPrimaryKey(params.get("batch_no"));
			//更新订单状态为退款状态
			if (status == 1) {
				orderCoreService.updateOrder(7, refund1.getOrderId());
			}
		} else {
			throw new BizException("支付宝退款验签失败");
		}
		PaymentNotifyResponse abstractResponse=new PaymentNotifyResponse();
		abstractResponse.setResult("success");
		return abstractResponse;
	}
}