
package com.gpmall.pay.biz.abs;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.dto.OrderDetailRequest;
import com.gpmall.order.dto.OrderDetailResponse;
import com.gpmall.pay.utils.ParamValidatorUtils;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;
import com.gupaoedu.pay.constants.RefundCodeEnum;
import com.gupaoedu.pay.dto.PaymentRequest;
import com.gupaoedu.pay.dto.RefundRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 验证器基类
 * @author 
 */
public abstract class BaseValidator implements Validator {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void validate(AbstractRequest request) {
        //基本参数校验
        ParamValidatorUtils.validateV2(request);
        //特殊校验
        specialValidate(request);
    }

    public abstract void specialValidate(AbstractRequest request);

    /**
     * 检验订单的公共方法
     * @param request
     * @param orderQueryService
     */
    public void commonValidate(AbstractRequest request, OrderQueryService orderQueryService) {
        if (request instanceof PaymentRequest) {
            PaymentRequest paymentRequest = (PaymentRequest) request;
            //校验订单是否存在
            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
            orderDetailRequest.setOrderId(paymentRequest.getTradeNo());
            OrderDetailResponse orderDetailResponse = orderQueryService.orderDetail(orderDetailRequest);
            if (null == orderDetailResponse) {
                throw new BizException(PayReturnCodeEnum.NO_ORDER_NOT_EXIST.getCode(), PayReturnCodeEnum.NO_ORDER_NOT_EXIST.getMsg());
            }
            //校验订单是否已支付，同一订单支付幂等处理
            if (!Objects.equals(orderDetailResponse.getStatus(), 0)) {
                throw new BizException(PayReturnCodeEnum.HAD_PAY_ERROR.getCode(), PayReturnCodeEnum.HAD_PAY_ERROR.getMsg());
            }
            // 防止金额篡改等
            if(orderDetailResponse.getPayment().compareTo(paymentRequest.getOrderFee())!=0){
                throw new BizException(PayReturnCodeEnum.ORDER_AMOUNT_ERROR.getCode(),PayReturnCodeEnum.ORDER_AMOUNT_ERROR.getMsg());
            }
        }
        //如果是退款请求
        else if (request instanceof RefundRequest) {
            RefundRequest refundRequest = (RefundRequest) request;
            //校验订单号是否正确
            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
            orderDetailRequest.setOrderId(refundRequest.getOrderId());
            OrderDetailResponse orderDetailResponse = orderQueryService.orderDetail(orderDetailRequest);
            if (null == orderDetailResponse) {
                throw new BizException(PayReturnCodeEnum.SYS_PARAM_NOT_RIGHT.getCode(), PayReturnCodeEnum.SYS_PARAM_NOT_RIGHT.getMsg());
            }
            //检查订单是否已退款
            if (Objects.equals(orderDetailResponse.getStatus(), 7)) {
                throw new BizException(RefundCodeEnum.ORDER_HAD_REFUND.getCode(), RefundCodeEnum.ORDER_HAD_REFUND.getMsg());
            }
        }
    }
}
