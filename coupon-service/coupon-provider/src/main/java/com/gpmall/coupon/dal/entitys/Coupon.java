package com.gpmall.coupon.dal.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Coupon implements Serializable {
    private Long id;

    private String name;

    private String desc;

    private Byte type;

    private BigDecimal discountPrice;

    private BigDecimal fullPrice;

    private Boolean isVisible;

    private Integer count;

    private Date grabStartTime;

    private Date grabEndTime;

    private Date startTime;

    private Date endTime;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getGrabStartTime() {
        return grabStartTime;
    }

    public void setGrabStartTime(Date grabStartTime) {
        this.grabStartTime = grabStartTime;
    }

    public Date getGrabEndTime() {
        return grabEndTime;
    }

    public void setGrabEndTime(Date grabEndTime) {
        this.grabEndTime = grabEndTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", type=").append(type);
        sb.append(", discountPrice=").append(discountPrice);
        sb.append(", fullPrice=").append(fullPrice);
        sb.append(", isVisible=").append(isVisible);
        sb.append(", count=").append(count);
        sb.append(", grabStartTime=").append(grabStartTime);
        sb.append(", grabEndTime=").append(grabEndTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}