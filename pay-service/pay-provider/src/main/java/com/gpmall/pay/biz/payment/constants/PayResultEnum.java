package com.gpmall.pay.biz.payment.constants;

import com.gupaoedu.pay.IEnum;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
public enum PayResultEnum implements IEnum {

    TRADE_PROCESSING("1","支付处理中"),
    TRADE_FINISHED ("2","支付完成"),
    TRADE_SUCCESS ("3","支付成功"),
    FAIL("4","支付失败");

    private String code;

    private String desc;

    PayResultEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
