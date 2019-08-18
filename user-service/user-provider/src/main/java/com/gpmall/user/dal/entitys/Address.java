package com.gpmall.user.dal.entitys;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "tb_address")
@Data
@ToString
public class Address {
    @Id
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    private String tel;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "is_default")
    private Integer isDefault;


}