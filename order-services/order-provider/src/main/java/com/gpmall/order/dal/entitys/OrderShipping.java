package com.gpmall.order.dal.entitys;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_order_shipping")
public class OrderShipping {
    /**
     * 订单ID
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 收货人全名
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 固定电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 移动电话
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 省份
     */
    @Column(name = "receiver_state")
    private String receiverState;

    /**
     * 城市
     */
    @Column(name = "receiver_city")
    private String receiverCity;

    /**
     * 区/县
     */
    @Column(name = "receiver_district")
    private String receiverDistrict;

    /**
     * 收货地址，如：xx路xx号
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 邮政编码,如：310001
     */
    @Column(name = "receiver_zip")
    private String receiverZip;

    private Date created;

    private Date updated;

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取收货人全名
     *
     * @return receiver_name - 收货人全名
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 设置收货人全名
     *
     * @param receiverName 收货人全名
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 获取固定电话
     *
     * @return receiver_phone - 固定电话
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * 设置固定电话
     *
     * @param receiverPhone 固定电话
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    /**
     * 获取移动电话
     *
     * @return receiver_mobile - 移动电话
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * 设置移动电话
     *
     * @param receiverMobile 移动电话
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    /**
     * 获取省份
     *
     * @return receiver_state - 省份
     */
    public String getReceiverState() {
        return receiverState;
    }

    /**
     * 设置省份
     *
     * @param receiverState 省份
     */
    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    /**
     * 获取城市
     *
     * @return receiver_city - 城市
     */
    public String getReceiverCity() {
        return receiverCity;
    }

    /**
     * 设置城市
     *
     * @param receiverCity 城市
     */
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    /**
     * 获取区/县
     *
     * @return receiver_district - 区/县
     */
    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    /**
     * 设置区/县
     *
     * @param receiverDistrict 区/县
     */
    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    /**
     * 获取收货地址，如：xx路xx号
     *
     * @return receiver_address - 收货地址，如：xx路xx号
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * 设置收货地址，如：xx路xx号
     *
     * @param receiverAddress 收货地址，如：xx路xx号
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    /**
     * 获取邮政编码,如：310001
     *
     * @return receiver_zip - 邮政编码,如：310001
     */
    public String getReceiverZip() {
        return receiverZip;
    }

    /**
     * 设置邮政编码,如：310001
     *
     * @param receiverZip 邮政编码,如：310001
     */
    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
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