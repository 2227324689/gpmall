package com.gpmall.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author GP17513-成都-Rigel
 * @description 订单详细信息
 * @date 2019/8/8 14:33
 **/
@Data
public class OrderDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orderId;

    private Double payment;

    private Integer paymentType;

    private Double postFee;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date paymentTime;

    private Date consignTime;

    private Date endTime;

    private Date closeTime;

    private String shippingName;

    private String shippingCode;

    private Long userId;

    private String buyerMessage;

    private String buyerNick;

    private Integer buyerComment;

    private List<OrderItemDto> orderItemDto;

    private OrderShippingDto orderShippingDto;
}
