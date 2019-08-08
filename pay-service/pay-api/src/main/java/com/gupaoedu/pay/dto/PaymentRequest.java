package com.gupaoedu.pay.dto;

import com.gpmall.commons.result.AbstractRequest;
import lombok.Data;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Data
public class PaymentRequest extends AbstractRequest{
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 交易订单号, 统一生成全局唯一的订单号
     */
    private String tradeNo;

    /**
     * 实际支付金额(单位为分)
     */
    private Integer totalFee;

    /**
     * 订单总金额
     */
    private Integer orderFee;

    /**
     * 商品描述(必填)
     * 微信支付提交格式要求；支付宝不需要严格控制格式
     * {浏览器打开的网站主页title名 -商品概述}
     */
    private String subject;

    /**
     * 终端IP(非必填)
     */
    private String spbillCreateIp;

    /**
     * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
     */
    private String payChannel;

    @Override
    public void requestCheck() {

    }
}
