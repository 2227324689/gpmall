package com.gpmall.pay.biz.payment.validator;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.pay.biz.abs.BaseValidator;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentRequest;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Service("aliPaymentValidator")
public class AliPaymentValidator extends BaseValidator {

    @Override
    public void validate(AbstractRequest request) {
        if(request==null){
            throw new ValidateException(PayReturnCodeEnum.REQUISITE_PARAMETER_NOT_EXIST.getCode(),PayReturnCodeEnum.REQUISITE_PARAMETER_NOT_EXIST.getMsg());
        }
        PaymentRequest paymentRequest=(PaymentRequest)request;

    }
}
