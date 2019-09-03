package com.gpmall.pay.biz.abs;

import java.math.BigDecimal;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
public class PaymentContext extends Context {
    /** 商户订单号（必填）*/
    private String outTradeNo;
    /** 总金额(单位为元)（必填）*/
    private BigDecimal totalFee;
    /** 用户id **/
    private Long userId;
    /** 返回参数 构建html表单 */
    private String htmlStr;


    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public PaymentContext() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
