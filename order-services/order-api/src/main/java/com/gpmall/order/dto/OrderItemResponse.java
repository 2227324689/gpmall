package com.gpmall.order.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author heps
 * @date 2019/8/13 21:39
 */
@Data
public class OrderItemResponse extends AbstractResponse {

    private String id;

    private String itemId;

    private String orderId;

    private Integer num;

    private String title;

    private BigDecimal price;

    private BigDecimal totalFee;

    private String picPath;

    private OrderDto orderDto;
}
