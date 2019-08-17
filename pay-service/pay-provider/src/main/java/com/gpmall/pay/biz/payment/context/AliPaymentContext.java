package com.gpmall.pay.biz.payment.context;


import com.gpmall.pay.biz.abs.PaymentContext;

import java.util.SortedMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 * 支付宝支付的上下文信息
 */
public class AliPaymentContext extends PaymentContext {
    /** 商品名称（必填）*/
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
