package com.gpmall.pay.biz.payment.context;

import com.gpmall.pay.biz.abs.RefundContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author： wz
 * @Date: 2019-08-16 11:28
 **/
@Service
public class WechatRefundContext extends RefundContext {

	/** 返回参数 构建退款xml表单 */
	private String xml;
	/**
	 * 订单号
	 */
	private String outTradeNo;

	/**
	 * 微信退款金额
	 */
	private BigDecimal refundFee;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public BigDecimal getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
}