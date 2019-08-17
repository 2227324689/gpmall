package com.gpmall.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/31-19:34
 */
@Data
public class AddressDto implements Serializable {

    private Long addressId;

    private Long userId;

    private String userName;

    private String tel;

    private String streetName;

    private Integer isDefault;
}
