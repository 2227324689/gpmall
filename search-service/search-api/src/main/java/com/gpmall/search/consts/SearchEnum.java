package com.gpmall.search.consts;


/**
 * 常量枚举类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public enum SearchEnum {
	/**
	 * 成功响应码
	 */
	SUCCESS("Z0000", "成功"),
	/**
	 * 失败响应码
	 */
	FAILED("Z0001", "失败，详情见附属信息"),
	/**
	 * 参数为空
	 */
	STRING_EMPTY("Z0002", "入参字符串为空，%s");
	private String code;

	private String msg;

	private SearchEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String param(Object o) {
		return String.format(msg, o);
	}

	public String getMsg(String code) {
		return msg + ":" + code;
	}

	@Override
	public String toString() {
		return "SearchEnum{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				'}';
	}
}
