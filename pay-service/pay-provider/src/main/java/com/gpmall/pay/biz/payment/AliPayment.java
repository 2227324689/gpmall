package com.gpmall.pay.biz.payment;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.PaymentContext;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Slf4j
@Service
public class AliPayment extends BasePayment {

	@Resource(name = "aliPaymentValidator")
	private Validator validator;

	@Autowired
	AliPaymentConfig aliPaymentConfig;

	@Autowired
	PaymentMapper paymentMapper;

	@Override
	public Validator getValidator() {
		return validator;
	}

	@Override
	public Context createContext(AbstractRequest request) {

		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(request));
		//如果是退款业务
		if (jsonObject.containsKey("refundFlag")) {
			AliRefundContext aliRefundContext = new AliRefundContext();
			RefundRequest refundRequest = (RefundRequest) request;
			aliRefundContext.setTotalFee(refundRequest.getRefundAmount());
			aliRefundContext.setOutTradeNo(refundRequest.getOrderId());
			return aliRefundContext;
		}
		//如果是支付业务
		AliPaymentContext aliPaymentContext = new AliPaymentContext();
		PaymentRequest paymentRequest = (PaymentRequest) request;
		aliPaymentContext.setSubject(paymentRequest.getSubject());
		aliPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
		aliPaymentContext.setTotalFee(paymentRequest.getTotalFee());
		return aliPaymentContext;
	}

	@Override
	public void prepare(AbstractRequest request, Context context) throws BizException {
		SortedMap<String, Object> sParaTemp = new TreeMap<String, Object>();
		sParaTemp.put("partner", aliPaymentConfig.getAli_partner());
		sParaTemp.put("_input_charset", aliPaymentConfig.getInput_charset());
		//如果是退款上下文，填充退款参数
		if (context instanceof AliRefundContext) {
			AliRefundContext aliRefundContext = (AliRefundContext) context;
			sParaTemp.put("service", aliPaymentConfig.getRefund_service());
			sParaTemp.put("seller_user_id", aliPaymentConfig.getSeller_id());
			sParaTemp.put("refund_date", UtilDate.getDateFormatter());
			sParaTemp.put("batch_no", aliRefundContext.getOutTradeNo());
			sParaTemp.put("batch_num", "1");
			sParaTemp.put("notify_url", aliPaymentConfig.getRefund_notify_url());
			//退款详细数据，必填，格式（支付宝交易号^退款金额^备注），多笔请用#隔开
			String detail_data = aliRefundContext.getOutTradeNo() + "^" + aliRefundContext.getTotalFee().toString() +
					"^" + "正常退款";
			sParaTemp.put("detail_data", detail_data);
			aliRefundContext.setsParaTemp(sParaTemp);
			return;
		}
		//如果是支付上下文，填充支付参数
		AliPaymentContext aliPaymentContext = (AliPaymentContext) context;
		sParaTemp.put("service", aliPaymentConfig.getAli_service());
		sParaTemp.put("seller_email", aliPaymentConfig.getSeller_email());
		sParaTemp.put("seller_id", aliPaymentConfig.getSeller_id());
		sParaTemp.put("payment_type", 1);
		sParaTemp.put("it_b_pay", aliPaymentConfig.getIt_b_pay());
		sParaTemp.put("notify_url", aliPaymentConfig.getNotify_url());
		sParaTemp.put("return_url", aliPaymentConfig.getReturn_url());
		sParaTemp.put("out_trade_no", aliPaymentContext.getOutTradeNo());
		sParaTemp.put("subject", aliPaymentContext.getSubject());
		sParaTemp.put("total_fee", aliPaymentContext.getTotalFee().doubleValue());
		aliPaymentContext.setsParaTemp(sParaTemp);
	}


	@Override
	public AbstractResponse generalProcess(AbstractRequest request, Context context) throws BizException {
		Map<String, Object> sPara = AlipayBuildRequest.buildRequestParam(context.getsParaTemp(), aliPaymentConfig);
		String strPara = AlipayBuildRequest.buildRequest(sPara, "get", "确认", aliPaymentConfig);
		AbstractResponse response;
		if (context instanceof AliRefundContext) {
			response = new RefundResponse();
			((RefundResponse) response).setAlipayForm(strPara);
		} else {
			response = new PaymentResponse();
			((PaymentResponse) response).setHtmlStr(strPara);
		}
		response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
		response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
		return response;
	}


    @Override
    public void afterProcess(AbstractRequest request, AbstractResponse respond, PaymentContext context) throws BizException {
        log.info("Alipayment begin - afterProcess -request:"+request+"\n response:"+respond);
        PaymentRequest paymentRequest=(PaymentRequest)request;
        PaymentResponse response=(PaymentResponse)respond;
        com.gpmall.pay.dal.entitys.Payment payment=new Payment();
        payment.setCreateTime(new Date());
        payment.setId(UUID.randomUUID().toString());
        BigDecimal amount=new BigDecimal(paymentRequest.getOrderFee()/100);
        payment.setOrderAmount(amount);
        payment.setOrderId(paymentRequest.getTradeNo());
        payment.setPayerAmount(amount);
        payment.setPayerUid(paymentRequest.getUserId());
        payment.setPayerName("");//TODO
        payment.setPayWay(paymentRequest.getPayChannel());
        payment.setProductName(paymentRequest.getSubject());
        payment.setStatus(PayResultEnum.TRADE_PROCESSING.getCode());//
        payment.setRemark("");
        payment.setPayNo(response.getPrepayId());//第三方的交易id
        payment.setUpdateTime(new Date());
        paymentMapper.insert(payment);
    }

    @Override
    public String getPayChannel() {
        return PayChannelEnum.ALI_PAY.getCode();
    }

    @Override
    public AbstractResponse completePayment(AbstractRequest request) throws BizException {
        PaymentNotifyRequest paymentNotifyRequest=(PaymentNotifyRequest)request;

        Map requestParams = paymentNotifyRequest.getResultMap();
        Map<String, Object> params = new HashMap<>(requestParams.size());
        requestParams.forEach((key,value)->{
            String[] values = (String[]) value;
            params.put((String)key,StringUtils.join(values, ","));
        });

        PaymentNotifyResponse response = new PaymentNotifyResponse();
        //验证
        if (AlipayNotify.verify(params, aliPaymentConfig)) {
            //TODO 判断交易状态
            //TRADE_FINISH(支付完成)、TRADE_SUCCESS(支付成功)、FAIL(支付失败)
            String tradeStatus = params.get("trade_status").toString();
            //TODO 更新交易表的交易状态
            response.setResult("success");
        } else {
            throw new BizException("支付宝签名验证失败");
        }
        response.setResult("fail");
        return response;
    }
}
