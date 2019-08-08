package com.gpmall.pay.biz.payment.validator;

import com.gpmall.pay.biz.abs.BaseValidator;
import com.gupaoedu.pay.dto.PaymentRequest;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Service("wechatPaymentValidator")
public class WechatPaymentValidator extends BaseValidator {

    @Override
    public void specialValidate(PaymentRequest paymentRequest) {
        //TODO  待实现
    }
}
