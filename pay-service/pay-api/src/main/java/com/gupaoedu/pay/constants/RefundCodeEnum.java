package com.gupaoedu.pay.constants;

/**
 * @Description: 退款异常枚举
 * @Author： wz
 * @Date: 2019-08-21 20:18
 **/
public enum RefundCodeEnum {
	ORDER_HAD_REFUND                 ("007001","该订单已退款"),
	REFUND_NOTIFY_PASRE_FAIL         ("007002","退款通知解密失败");

	private String code;

	private String msg;

	private RefundCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getMsg(String code) {
		return msg + ":" + code;
	}

	public String getCodeString() {
		return getCode() + "";
	}

	@Override
	public String toString() {
		return "RefundCodeEnum{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				'}';

	}
}
