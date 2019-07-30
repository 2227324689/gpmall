package com.gpmall.order.dto;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.order.constant.OrderRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午9:49
 */
@Data
public class CreateOrderRequest extends AbstractRequest{


    private String userId;

    private Long addressId;

    private String tel;

    private String userName;

    private String streetName;

    private BigDecimal orderTotal;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(userId)||addressId==null||
                StringUtils.isBlank(tel)||StringUtils.isBlank(userName)|| StringUtils.isBlank(streetName)||orderTotal==null){
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
