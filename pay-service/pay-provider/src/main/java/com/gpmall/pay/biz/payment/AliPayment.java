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

    @Resource(name="aliPaymentValidator")
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
    public PaymentContext createContext(AbstractRequest request) {
        AliPaymentContext aliPaymentContext=new AliPaymentContext();
        PaymentRequest paymentRequest=(PaymentRequest)request;
        aliPaymentContext.setSubject(paymentRequest.getSubject());
        aliPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
        aliPaymentContext.setTotalFee(paymentRequest.getTotalFee());
        return aliPaymentContext;
    }

    @Override
    public void prepare(AbstractRequest request, PaymentContext context) throws BizException {
        SortedMap<String, Object> sParaTemp =new TreeMap<String, Object>();
        AliPaymentContext aliPaymentContext=(AliPaymentContext)context;
        sParaTemp.put("service", aliPaymentConfig.getAli_service());
        sParaTemp.put("partner", aliPaymentConfig.getAli_partner());
        sParaTemp.put("seller_email", aliPaymentConfig.getSeller_email());
        sParaTemp.put("seller_id", aliPaymentConfig.getSeller_id());
        sParaTemp.put("_input_charset", aliPaymentConfig.getInput_charset());
        sParaTemp.put("payment_type", 1);
        sParaTemp.put("it_b_pay", aliPaymentConfig.getIt_b_pay());
        sParaTemp.put("notify_url", aliPaymentConfig.getNotify_url());
        sParaTemp.put("return_url",aliPaymentConfig.getReturn_url());
        sParaTemp.put("out_trade_no",aliPaymentContext.getOutTradeNo());
        sParaTemp.put("subject", aliPaymentContext.getSubject());
        sParaTemp.put("total_fee", aliPaymentContext.getTotalFee().doubleValue());
        aliPaymentContext.setsParaTemp(sParaTemp);
    }


    @Override
    public AbstractResponse generalProcess(AbstractRequest request, PaymentContext context) throws BizException {
        PaymentResponse response=new PaymentResponse();
        AliPaymentContext aliPaymentContext=(AliPaymentContext)context;
        Map<String, Object> sPara = AlipayBuildRequest.buildRequestParam(aliPaymentContext.getsParaTemp(),aliPaymentConfig);
        String strPara = AlipayBuildRequest.buildRequest(sPara, "get", "确认",aliPaymentConfig);
        response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
        response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
        response.setHtmlStr(strPara);
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
