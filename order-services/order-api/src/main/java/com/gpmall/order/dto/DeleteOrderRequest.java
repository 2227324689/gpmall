package com.gpmall.order.dto;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.order.constant.OrderRetCode;
import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午9:57
 */

@Data
public class DeleteOrderRequest extends AbstractRequest{

    private Long orderId;

    @Override
    public void requestCheck() {
        if(orderId==null){
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
