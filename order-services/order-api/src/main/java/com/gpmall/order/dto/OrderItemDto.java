package com.gpmall.order.dto;/**
 * Created by mic on 2019/7/31.
 */

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/31-上午9:53
 */
@Data
public class OrderItemDto implements Serializable{
    private String id;

    private String itemId;

    private String orderId;

    private Integer num;

    private String title;

    private BigDecimal price;

    private BigDecimal totalFee;

    private String picPath;
}
