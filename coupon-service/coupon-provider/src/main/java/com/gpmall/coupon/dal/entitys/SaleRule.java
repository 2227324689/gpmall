package com.gpmall.coupon.dal.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_sale_rule")
public class SaleRule implements Serializable {
    @Id
    private Long id;

    /**
     * 活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null
     */
    @Column(name = "acti_id")
    private Long actiId;

    /**
     * 满足规则条件的商品id
     */
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "full_price")
    private BigDecimal fullPrice;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "discount_rate")
    private BigDecimal discountRate;

    @Column(name = "gift_item_id")
    private Long giftItemId;

    /**
     * 是否可叠加规则
     */
    private Boolean overlap;

    private String desc;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null
     *
     * @return acti_id - 活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null
     */
    public Long getActiId() {
        return actiId;
    }

    /**
     * 设置活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null
     *
     * @param actiId 活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null
     */
    public void setActiId(Long actiId) {
        this.actiId = actiId;
    }

    /**
     * 获取满足规则条件的商品id
     *
     * @return item_id - 满足规则条件的商品id
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置满足规则条件的商品id
     *
     * @param itemId 满足规则条件的商品id
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return full_price
     */
    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    /**
     * @param fullPrice
     */
    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    /**
     * @return discount_price
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * @param discountPrice
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * @return discount_rate
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * @param discountRate
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * @return gift_item_id
     */
    public Long getGiftItemId() {
        return giftItemId;
    }

    /**
     * @param giftItemId
     */
    public void setGiftItemId(Long giftItemId) {
        this.giftItemId = giftItemId;
    }

    /**
     * 获取是否可叠加规则
     *
     * @return overlap - 是否可叠加规则
     */
    public Boolean getOverlap() {
        return overlap;
    }

    /**
     * 设置是否可叠加规则
     *
     * @param overlap 是否可叠加规则
     */
    public void setOverlap(Boolean overlap) {
        this.overlap = overlap;
    }

    /**
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}