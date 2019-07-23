package com.gpmall.shopping.constants;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-16:45
 * user-service统一错误码为  004
 */
public enum ShoppingRetCode {
    // 系统公用
    SUCCESS                             ("000000", "成功"),

    REQUISITE_PARAMETER_NOT_EXIST       ("004073", "必要的参数不能为空"),

    DB_EXCEPTION                        ("004097", "数据库异常"),
    SYSTEM_TIMEOUT                      ("004098", "系统超时"),
    SYSTEM_ERROR                        ("004099", "系统错误");
    private String code;
    private String message;

    ShoppingRetCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String code) {
        for (ShoppingRetCode s : ShoppingRetCode.values()) {
            if (null == code)
                break;
            if (s.code.equals(code)) {
                return s.message;
            }
        }
        return null;
    }
}
