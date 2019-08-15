package com.gupaoedu.pay.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 退款返回对象
 * @Author： wz
 * @Date: 2019-08-15 16:38
 **/
@Data
public class RefundResponse extends AbstractResponse {

	/**
	 * 发起退款流水号
	 */
	private String refundPlatformNo;
	/**
	 * 返回退款单号
	 */
	private String refundNo;
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;

	/**
	 * 退款状态
	 */
	private Integer refundStatus;

	/**
	 * 支付宝网站支付表单
	 */
	private String alipayForm;

	/**
	 * 退款备注
	 */
	private String refundRemarks;
}