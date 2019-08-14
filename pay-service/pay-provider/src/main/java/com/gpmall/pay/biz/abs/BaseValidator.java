
package com.gpmall.pay.biz.abs;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.pay.utils.ParamValidatorUtils;
import com.gupaoedu.pay.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证器基类
 * @author 
 */
public abstract class BaseValidator implements Validator {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void validate(AbstractRequest request) {

        PaymentRequest paymentRequest=(PaymentRequest)request;
        //基本参数校验
        ParamValidatorUtils.validateV2(paymentRequest);
        //特殊校验
        specialValidate(paymentRequest);

    }

    public abstract void specialValidate(PaymentRequest paymentRequest);
}
