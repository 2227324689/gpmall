package com.gupaoedu.pay.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gupaoedu.pay.validatorextend.PayChannel;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description: 退款请求对象(包含支付宝和微信)
 * @Author： wz
 * @Date: 2019-08-15 16:39
 **/
@Data
public class RefundRequest extends AbstractRequest {
	/**
	 * 用户id
	 */
	@NotNull(message = "userId不可为空")
	private Long userId;
	/**
	 * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
	 */
	@PayChannel
	private String payChannel;
	/**
	 * 支付订单号
	 */
	@NotNull(message = "原交易订单号不能为空")
	private String orderId;

	/**
	 * 申请退款金额
	 */
	@NotNull(message = "退款金额不能为空")
	@DecimalMin(value = "0.01",message = "退款最小金额不能小于0.01元")
	private BigDecimal refundAmount;

	@Override
	public void requestCheck() {

	}
}