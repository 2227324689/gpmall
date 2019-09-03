package com.gpmall.order.dal.entitys;



import lombok.Data;



import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.*;



@Table(name = "tb_order")

@Data

public class Order {

    /**

     * 订单id

     */
    @Id
    private String orderId;



    /**

     * 实付金额

     */

    private BigDecimal payment;



    /**

     * 支付类型 1在线支付 2货到付款

     */

    @Column(name = "payment_type")

    private Integer paymentType;



    /**

     * 邮费

     */

    @Column(name = "post_fee")

    private BigDecimal postFee;



    /**

     * 状态 0未付款 1已付款 2未发货 3已发货 4交易成功 5交易关闭 6交易失败

     */

    private Integer status;



    /**

     * 订单创建时间

     */

    @Column(name = "create_time")

    private Date createTime;



    /**

     * 订单更新时间

     */

    @Column(name = "update_time")

    private Date updateTime;



    /**

     * 付款时间

     */

    @Column(name = "payment_time")

    private Date paymentTime;



    /**

     * 发货时间

     */

    @Column(name = "consign_time")

    private Date consignTime;



    /**

     * 交易完成时间

     */

    @Column(name = "end_time")

    private Date endTime;



    /**

     * 交易关闭时间

     */

    @Column(name = "close_time")

    private Date closeTime;



    /**

     * 物流名称

     */

    @Column(name = "shipping_name")

    private String shippingName;



    /**

     * 物流单号

     */

    @Column(name = "shipping_code")

    private String shippingCode;



    /**

     * 用户id

     */

    @Column(name = "user_id")

    private Long userId;



    /**

     * 买家留言

     */

    @Column(name = "buyer_message")

    private String buyerMessage;



    /**

     * 买家昵称

     */

    @Column(name = "buyer_nick")

    private String buyerNick;



    /**

     * 买家是否已经评价

     */

    @Column(name = "buyer_comment")

    private Integer buyerComment;





}
