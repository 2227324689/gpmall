package com.gpmall.pay.biz.payment.validator;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.dto.OrderDetailRequest;
import com.gpmall.order.dto.OrderDetailResponse;
import com.gpmall.pay.biz.abs.BaseValidator;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentRequest;
import com.gupaoedu.pay.dto.RefundRequest;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Service("wechatPaymentValidator")
public class WechatPaymentValidator extends BaseValidator {
     @Reference(timeout=3000)
     OrderQueryService orderQueryService;
    @Override
    public void specialValidate(AbstractRequest request) {
        commonValidate(request,orderQueryService);
    }
}
