package com.gupaoedu.pay.constants;


import com.gupaoedu.pay.IEnum;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
public enum PayChannelEnum implements IEnum{

    ALI_PAY("ali_pay","支付宝支付渠道"),
    WECHAT_PAY("wechat_pay","微信支付渠道"),
    ALI_REFUND("ali_refund","支付宝退款渠道"),
    WECHAT_REFUND("wechat_refund","微信退款渠道");


    private String code;

    private String desc;

    PayChannelEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
