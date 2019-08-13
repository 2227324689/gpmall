package com.gpmall.order.dto;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.order.constant.OrderRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午9:49
 */
@Data
public class CreateOrderRequest extends AbstractRequest{


    private Long userId;

    private Long addressId;

    private String tel;

    private String userName;

    private String streetName;

    private BigDecimal orderTotal;

    List<CartProductDto> cartProductDtoList;//购物车中的商品列表

    //如果需要在web层做防重复提交  可以基于session的方式，访问订单页面预先生成一个token，下发给浏览器，浏览器提交
    //的时候带着token请求服务端，服务端收到请求删除token，下次在请求时服务端的token被删除了，提示订单重复提交
    private String uniqueKey; //业务唯一id


    @Override
    public void requestCheck() {
        if(userId==null||addressId==null||
                StringUtils.isBlank(tel)||StringUtils.isBlank(userName)|| StringUtils.isBlank(streetName)||orderTotal==null){
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
