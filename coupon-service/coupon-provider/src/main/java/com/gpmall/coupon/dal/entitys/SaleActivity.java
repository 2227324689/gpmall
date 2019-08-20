package com.gpmall.coupon.dal.entitys;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_sale_acti")
@Data
public class SaleActivity implements Serializable {
    @Id
    private Long id;

    private String name;

    /**
     * 活动类型
     */
    private Integer type;

    /**
     * 状态 0 未开始 1 进行中 2 已结束
     */
    private Byte status;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private String desc;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}