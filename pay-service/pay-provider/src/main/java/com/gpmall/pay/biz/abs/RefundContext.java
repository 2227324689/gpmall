package com.gpmall.pay.biz.abs;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 退款上下文
 * @Author： wz
 * @Date: 2019-08-16 00:08
 **/
@Data
public class RefundContext extends Context{
	/** 商城退款流水号*/
	private String refundPlatformNo;
	/**用户id**/
	private Long userId;

}