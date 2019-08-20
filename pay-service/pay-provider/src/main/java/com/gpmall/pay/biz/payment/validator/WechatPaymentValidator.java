package com.gpmall.pay.biz.payment.validator;

import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.dto.OrderDetailRequest;
import com.gpmall.order.dto.OrderDetailResponse;
import com.gpmall.pay.biz.abs.BaseValidator;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.dto.PaymentRequest;
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
    public void specialValidate(PaymentRequest paymentRequest) {
 /*       //校验订单是否存在
        OrderDetailRequest orderDetailRequest=new OrderDetailRequest();
        orderDetailRequest.setOrderId(paymentRequest.getTradeNo());
        OrderDetailResponse orderDetailResponse=orderQueryService.orderDetail(orderDetailRequest);
        if(null==orderDetailResponse){
            throw new BizException(PayReturnCodeEnum.NO_ORDER_NOT_EXIST.getCode(),PayReturnCodeEnum.NO_ORDER_NOT_EXIST.getMsg());
        }
        //校验订单是否已支付，同一订单支付幂等处理
        if(!Objects.equals(orderDetailResponse.getStatus(),0)){
            throw new BizException(PayReturnCodeEnum.HAD_PAY_ERROR.getCode(),PayReturnCodeEnum.HAD_PAY_ERROR.getMsg());
        }
*/
    }
}
