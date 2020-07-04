package com.gpmall.pay.biz.payment.validator;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.order.OrderQueryService;
import com.gpmall.pay.biz.abs.BaseValidator;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 *
 * @author 风骚的Michael 老师
 */
@Service("aliPaymentValidator")
public class AliPaymentValidator extends BaseValidator {
	@Reference(timeout = 3000)
	OrderQueryService orderQueryService;
	// 付款设置费用最小
	@Value("${pay.set.totalFee.min:false}")
	private boolean paySetTotalFeeMin;
	@Override
	public void specialValidate(AbstractRequest request) {
       super.commonValidate(request,orderQueryService,paySetTotalFeeMin);
	}
}
