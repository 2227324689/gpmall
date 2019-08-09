package com.gpmall.pay.biz.payment.validator.validatorextend;

import com.gpmall.pay.biz.payment.constants.PayChannelEnum;
import com.gpmall.pay.utils.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * description: 支付渠道 校验器
 *
 * @author shuangling.mao 1015952139
 * @date 2019-08-09 01:32
 */

public class PayChannelValidator implements ConstraintValidator<PayChannel, String> {
    private String payChannel = null;
    @Override
    public void initialize(PayChannel constraintAnnotation) {
        //可以在此做一些初始化工作  例如 从获取注解中的某些值
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EnumUtils.containsVal(PayChannelEnum.values(),value);
    }
}
