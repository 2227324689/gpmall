package com.gpmall.pay.biz.payment.constants;

/**
 * 支付相关常量
 * @author mic
 */
public class PaymentConstants {

    public enum TradeTypeEnum{
        JSAPI("JSAPI","微信公众号支付"),
        NATIVE("NATIVE","原生扫码支付"),
        APP("APP","app支付"),
        MICROPAY("MICROPAY","刷卡支付");
        private String type;

        private String desc;

        TradeTypeEnum(String type,String desc){
            this.type=type;
            this.desc=desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum PayStatusEnum{
        INIT_STATUS(0,"下单成功未支付"),
        PAY_SUCCESS(1,"支付成功"),
        PAY_FAILED(2,"支付失败"),
        PAY_BACK(3,"已退款");

        private Integer status;

        private String desc;

        PayStatusEnum(Integer status,String desc){
            this.status=status;
            this.desc=desc;
        }

        public Integer getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }
    }

}
