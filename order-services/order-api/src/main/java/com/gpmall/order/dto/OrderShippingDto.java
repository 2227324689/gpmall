package com.gpmall.order.dto;/**
 * Created by mic on 2019/7/31.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/31-上午9:53
 */
@Data
public class OrderShippingDto implements Serializable{

    private String orderId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverState;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
