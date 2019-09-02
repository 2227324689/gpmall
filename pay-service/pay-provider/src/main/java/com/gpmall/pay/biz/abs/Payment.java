package com.gpmall.pay.biz.abs;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import com.gupaoedu.pay.dto.PaymentNotifyRequest;
import com.gupaoedu.pay.dto.PaymentNotifyResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
public interface Payment<R extends AbstractResponse,P extends AbstractRequest> {

    /**
     * 发起交易执行的过程
     * @param request
     * @return
     * @throws BizException
     */
    R process(P request) throws BizException;

    /**
     * 完成交易结果的处理
     * @param request
     * @return
     * @throws BizException
     */
    PaymentNotifyResponse completePayment(PaymentNotifyRequest request) throws BizException;
}
