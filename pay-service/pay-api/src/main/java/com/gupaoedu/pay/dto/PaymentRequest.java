package com.gupaoedu.pay.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gupaoedu.pay.validatorextend.PayChannel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;


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
    @NotNull(message = "userId不可为空")
    private Long userId;

    /**
     * 交易订单号, 统一生成全局唯一的订单号
     */
    @NotBlank(message = "tradeNo不可为空")
    private String tradeNo;

    /**
     * 实际支付金额(单位为分)
     */
    @Min(value = 0, message = "实际支付金额不能为负数")
    private BigDecimal totalFee;

    /**
     * 订单总金额
     */
    @Min(value = 0, message = "订单总金额不能为负数")
    private BigDecimal orderFee;

    /**
     * 商品描述(必填)
     * 微信支付提交格式要求；支付宝不需要严格控制格式
     * {浏览器打开的网站主页title名 -商品概述}
     */
    @NotBlank(message = "商品描述不能为空")
    private String subject;

    /**
     * 终端IP(非必填)
     */
    private String spbillCreateIp;

    /**
     * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
     */
    @PayChannel
    private String payChannel;
    /**
     * 地址id
     */
    private Long addressId;

    @Override
    public void requestCheck() {

    }

    public String getSubject() {
        try {
            return new String(subject.getBytes(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
