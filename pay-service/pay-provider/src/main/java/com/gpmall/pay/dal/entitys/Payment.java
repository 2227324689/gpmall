package com.gpmall.pay.dal.entitys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_payment")
@Data
public class Payment {
    @Id
    private Long id;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;


    /**
     * 付款方支付金额
     */
    @Column(name = "payer_amount")
    private BigDecimal payerAmount;


    /**
     * 第三方返回单号
     */
    @Column(name = "pay_no")
    private String payNo;

/*
    */
/**
     * 支付流水号
     *//*

    @Column(name = "trade_no")
    private String tradeNo;
*/

    /**
     * 付款人id
     */
    @Column(name = "payer_uid")
    private Long payerUid;

    /**
     * 付款人姓名
     */
    @Column(name = "payer_name")
    private String payerName;


    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 支付方式
     */
    @Column(name = "pay_way")
    private String payWay;

    /**
     * 支付成功时间
     */
    @Column(name = "pay_success_time")
    private Date paySuccessTime;

    /**
     * 支付完成时间
     */
    @Column(name = "complete_time")
    private Date completeTime;

    /**
     * 备注
     */
    private String remark;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}