package com.gpmall.coupon.dal.entitys;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_coupon")
public class Coupon {
    @Id
    private Long id;

    private String name;

    private String desc;

    /**
     * // 1 满减券 2 通用0元减价券
     */
    private Byte type;

    /**
     * 优惠金额
     */
    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    /**
     * 满金额
     */
    @Column(name = "full_price")
    private BigDecimal fullPrice;

    /**
     * 是否可用
     */
    @Column(name = "is_visible")
    private Boolean isVisible;

    /**
     * 优惠券可生成的优惠码数量
     */
    private Integer count;

    /**
     * 抢券开始时间  备用
     */
    @Column(name = "grab_start_time")
    private Date grabStartTime;

    @Column(name = "grab_end_time")
    private Date grabEndTime;

    /**
     * 优惠券有效期
     */
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private Date created;

    private Date updated;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取// 1 满减券 2 通用0元减价券
     *
     * @return type - // 1 满减券 2 通用0元减价券
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置// 1 满减券 2 通用0元减价券
     *
     * @param type // 1 满减券 2 通用0元减价券
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取优惠金额
     *
     * @return discount_price - 优惠金额
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * 设置优惠金额
     *
     * @param discountPrice 优惠金额
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * 获取满金额
     *
     * @return full_price - 满金额
     */
    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    /**
     * 设置满金额
     *
     * @param fullPrice 满金额
     */
    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    /**
     * 获取是否可用
     *
     * @return is_visible - 是否可用
     */
    public Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * 设置是否可用
     *
     * @param isVisible 是否可用
     */
    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * 获取优惠券可生成的优惠码数量
     *
     * @return count - 优惠券可生成的优惠码数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置优惠券可生成的优惠码数量
     *
     * @param count 优惠券可生成的优惠码数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取抢券开始时间  备用
     *
     * @return grab_start_time - 抢券开始时间  备用
     */
    public Date getGrabStartTime() {
        return grabStartTime;
    }

    /**
     * 设置抢券开始时间  备用
     *
     * @param grabStartTime 抢券开始时间  备用
     */
    public void setGrabStartTime(Date grabStartTime) {
        this.grabStartTime = grabStartTime;
    }

    /**
     * @return grab_end_time
     */
    public Date getGrabEndTime() {
        return grabEndTime;
    }

    /**
     * @param grabEndTime
     */
    public void setGrabEndTime(Date grabEndTime) {
        this.grabEndTime = grabEndTime;
    }

    /**
     * 获取优惠券有效期
     *
     * @return start_time - 优惠券有效期
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置优惠券有效期
     *
     * @param startTime 优惠券有效期
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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