package com.gpmall.search.consts;


/**
 * 常量枚举类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 * search-service统一错误码为  007
 */
public enum SearchRetCode {
	// 系统公用
	SUCCESS("000000", "成功"),
	/**
	 * 失败响应码
	 */
	FAILED("007001", "失败，详情见附属信息"),
	/**
	 * 参数为空
	 */
	STRING_EMPTY("007002", "入参字符串为空，%s"),

    REQUEST_CHECK_FAILURE               ("007003", "请求数据校验失败"),
    REQUISITE_PARAMETER_NOT_EXIST       ("007004", "必要的参数不能为空"),

	SYSTEM_TIMEOUT("007098", "系统超时"),
	SYSTEM_ERROR("007099", "系统错误");

	private String code;

	private String msg;

	private SearchRetCode(String code, String msg) {
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
		return "SearchRetCode{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				'}';
	}
}
