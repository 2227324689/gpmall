package com.gpmall.order.dal.entitys;



import lombok.Data;



import java.math.BigDecimal;

import javax.persistence.*;



@Table(name = "tb_order_item")

@Data

public class OrderItem {

    @Id

    private String id;



    /**

     * 商品id

     */

    @Column(name = "item_id")

    private String itemId;



    /**

     * 订单id

     */

    @Column(name = "order_id")

    private String orderId;



    /**

     * 商品购买数量

     */

    private Integer num;



    /**

     * 商品标题

     */

    private String title;



    /**

     * 商品单价

     */

    private Double price;



    /**

     * 商品总金额

     */

    @Column(name = "total_fee")

    private Double totalFee;



    /**

     * 商品图片地址

     */

    @Column(name = "pic_path")

    private String picPath;



}
