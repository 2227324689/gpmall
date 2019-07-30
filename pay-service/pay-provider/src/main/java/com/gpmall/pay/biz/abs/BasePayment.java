package com.gpmall.pay.biz.abs;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public abstract class BasePayment implements Payment{

    Logger Log=LoggerFactory.getLogger(BasePayment.class);

    public static Map<String,BasePayment> paymentMap=new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        paymentMap.put(getPayChannel(),this);
    }

    public abstract Validator getValidator();

    /**
     * 创建上下文信息
     *
     * @return
     */
    public abstract PaymentContext createContext(AbstractRequest request);


    /**
     * 为下层的支付渠道的数据做好准备
     *
     * @param request
     * @throws BizException
     */
    public abstract void prepare(AbstractRequest request, PaymentContext context) throws BizException;



    /**
     * 基本业务处理
     * @param request
     * @throws BizException
     */
    public abstract AbstractResponse generalProcess(AbstractRequest request, PaymentContext context) throws BizException;

    /***
     * 基本业务处理完成后执行的后续操作
     * @param request
     * @param respond
     * @param context
     * @return
     * @throws BizException
     */
    public abstract void afterProcess(AbstractRequest request, AbstractResponse respond, PaymentContext context) throws BizException;

    /**
     * 核心处理器
     */
    @Override
    public AbstractResponse process(AbstractRequest request) throws BizException {
        Log.info("Begin BasePayment.process:"+request);
        AbstractResponse response = null;
        PaymentContext context = createContext(request);
        //验证
        getValidator().validate(request);
        //准备
        prepare(request, context);
        //执行
        response = generalProcess(request, context);
        //善后
        afterProcess(request, response, context);
        return response;
    }

    /**
     * 获取支付渠道
     * @return
     */
    public abstract String getPayChannel();
}
