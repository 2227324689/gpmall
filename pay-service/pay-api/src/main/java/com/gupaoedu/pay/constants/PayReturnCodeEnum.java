package com.gupaoedu.pay.constants;

/**
 * 返回码对照表
 * 支付的错误码: 006-3位递增
 */
public enum PayReturnCodeEnum {

    SUCCESS                             ("000000", "成功"),


    SYS_PARAM_NOT_RIGHT                 ("006001", "请求参数不正确"),
    PAYMENT_PROCESSOR_FAILED            ("006002","支付失败"),

    REQUISITE_PARAMETER_NOT_EXIST       ("006073", "必要的参数不能为空"),

    DB_EXCEPTION                        ("006097", "数据库异常"),
    SYSTEM_TIMEOUT                      ("006098", "系统超时"),
    SYSTEM_ERROR                        ("006999", "系统繁忙,请稍后重试"),
    HAD_PAY_ERROR                       ("006099","该订单已支付"),
    NO_ORDER_NOT_EXIST                  ("006096","该订单不存在"),
    ORDER_AMOUNT_ERROR                  ("006094","订单金额不对");


    private String code;

    private String msg;

    private PayReturnCodeEnum(String code, String msg) {
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
        return msg+":"+code;
    }

    public String getCodeString(){
        return getCode()+"";
    }
    @Override
    public String toString() {
        return "PayReturnCodeEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
