package com.gpmall.search.consts;


/**
 * 常量枚举类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public enum SearchEnum {
    SUCCESS(10000, "成功"),
    FAILED(10001, "失败，详情见附属信息"),

    STRING_EMPTY(10002, "入参字符串为空，%s");
    private Integer code;

    private String msg;

    private SearchEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
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

    public String getCodeString() {
        return getCode() + "";
    }

    @Override
    public String toString() {
        return "SearchEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
