package com.gpmall.pay.biz.payment.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Service
public class WechatPaymentConfig {

    @Value("${wechat.wechat_appid}")
    private String wechatAppid;

    @Value("${wechat.wechat_mch_id}")
    private String wechatMch_id;

    @Value("${wechat.wechat_appsecret}")
    private String wechatAppsecret;

    @Value("${wechat.wechat_mchsecret}")
    private String wechatMchsecret;

    @Value("${wechat.wechat_notifyurl}")
    private String wechatNotifyurl;

    @Value("${wechat.wechat_unified_order}")
    private String wechatUnifiedOrder;

    @Value("${wechat.wechat_order_query}")
    private String checkOrderUrl;

    public String getWechatAppid() {
        return wechatAppid;
    }

    public String getWechatMch_id() {
        return wechatMch_id;
    }

    public String getWechatAppsecret() {
        return wechatAppsecret;
    }

    public String getWechatMchsecret() {
        return wechatMchsecret;
    }

    public String getWechatNotifyurl() {
        return wechatNotifyurl;
    }

    public String getWechatUnifiedOrder() {
        return wechatUnifiedOrder;
    }

    public String getCheckOrderUrl() {
        return checkOrderUrl;
    }
}
