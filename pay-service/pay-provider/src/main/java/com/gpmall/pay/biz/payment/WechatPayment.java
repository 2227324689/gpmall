package com.gpmall.pay.biz.payment;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.pay.biz.abs.BasePayment;
import com.gpmall.pay.biz.abs.PaymentContext;
import com.gpmall.pay.biz.abs.Validator;
import com.gpmall.pay.biz.payment.channel.wechatpay.WeChatBuildRequest;
import com.gpmall.pay.biz.payment.commons.HttpClientUtil;
import com.gupaoedu.pay.constants.PayChannelEnum;
import com.gpmall.pay.biz.payment.constants.PaymentConstants;
import com.gpmall.pay.biz.payment.constants.WechatPaymentConfig;
import com.gpmall.pay.biz.payment.context.WechatPaymentContext;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentNotifyRequest;
import com.gupaoedu.pay.dto.PaymentNotifyResponse;
import com.gupaoedu.pay.dto.PaymentRequest;
import com.gupaoedu.pay.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Service
public class WechatPayment extends BasePayment {

    @Autowired
    private WechatPaymentConfig wechatPaymentConfig;

    @Resource(name="wechatPaymentValidator")
    private Validator validator;

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public PaymentContext createContext(AbstractRequest request) {
        WechatPaymentContext wechatPaymentContext=new WechatPaymentContext();
        PaymentRequest paymentRequest=(PaymentRequest)request;
        wechatPaymentContext.setProductId(paymentRequest.getTradeNo());
        wechatPaymentContext.setSpbillCreateIp(paymentRequest.getSpbillCreateIp());
        wechatPaymentContext.setTradeType(PaymentConstants.TradeTypeEnum.NATIVE.getType());
        return wechatPaymentContext;
    }

    @Override
    public void prepare(AbstractRequest request, PaymentContext context) throws BizException {
        WechatPaymentContext wechatPaymentContext=(WechatPaymentContext)context;
        SortedMap<Object, Object> paraMap = new TreeMap<>();
        paraMap.put("body", wechatPaymentContext.getBody());
        paraMap.put("out_trade_no", wechatPaymentContext.getOutTradeNo());
        //单位分
        paraMap.put("total_fee", wechatPaymentContext.getTotalFee().intValue());
        paraMap.put("spbill_create_ip", wechatPaymentContext.getSpbillCreateIp());
        paraMap.put("appid", wechatPaymentConfig.getWechatAppid());
        paraMap.put("mch_id", wechatPaymentConfig.getWechatMch_id());
        paraMap.put("nonce_str", WeChatBuildRequest.getNonceStr());
        paraMap.put("trade_type", wechatPaymentContext.getTradeType());
        paraMap.put("product_id",wechatPaymentContext.getProductId());
        // 此路径是微信服务器调用支付结果通知路径
        paraMap.put("device_info","WEB");
        paraMap.put("notify_url", wechatPaymentConfig.getWechatNotifyurl());
        String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
        paraMap.put("sign", sign);
        String xml = WeChatBuildRequest.getRequestXml(paraMap);
        wechatPaymentContext.setXml(xml);
        //TODO 保存订单记录, 由于微信是属于预提交请求，需要对当前交易做幂等性，还需要控制二维码的时效
    }


    @Override
    public AbstractResponse generalProcess(AbstractRequest request, PaymentContext context) throws BizException {
        PaymentResponse response=new PaymentResponse();

        WechatPaymentContext wechatPaymentContext=(WechatPaymentContext)context;

        String xmlStr = HttpClientUtil.httpPost(wechatPaymentConfig.getWechatUnifiedOrder(), wechatPaymentContext.getXml());
        log.info("wechatPayment. generalProcess response：" + xmlStr);
        Map<String, String> resultMap = WeChatBuildRequest.doXMLParse(xmlStr);
        //TODO ???
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            if ("SUCCESS".equals(resultMap.get("result_code"))) {
                //表示订单处理成功
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
    public void afterProcess(AbstractRequest request, AbstractResponse respond, PaymentContext context) throws BizException {

    }

    @Override
    public String getPayChannel() {
        return PayChannelEnum.WECHAT_PAY.getCode();
    }

    @Override
    public AbstractResponse completePayment(AbstractRequest request) throws BizException {
        PaymentNotifyRequest paymentNotifyRequest=(PaymentNotifyRequest)request;
        PaymentNotifyResponse response=new PaymentNotifyResponse();
        SortedMap<Object, Object> paraMap = new TreeMap<>();
        Map<String,Object> resultMap=paymentNotifyRequest.getResultMap();
        for (Iterator iter = resultMap.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next().toString();
            String value =resultMap.get(name).toString();
            paraMap.put(name,value);
        }
        //组装返回的结果的签名字符串
        String rsSign=resultMap.remove("sign").toString();
        String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
        //验证签名
        if(rsSign.equals(sign)){
            //SUCCESS、FAIL
            String resultCode=resultMap.get("result_code").toString();
            if("SUCCESS".equals(resultCode)){
                //TODO 更新交易表的交易结果
                response.setResult(WeChatBuildRequest.setXML("SUCCESS","OK"));
            }else{
                //TODO 更新交易表交易结果为失败，交易失败，微信端不需要知道我们的处理结果
            }
        }else{
            throw new BizException("微信返回结果签名验证失败");
        }
        return response;
    }
}
