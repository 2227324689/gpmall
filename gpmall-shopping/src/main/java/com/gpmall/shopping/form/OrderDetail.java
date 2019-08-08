package com.gpmall.shopping.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/8-14:02
 */
@Data
public class OrderDetail {
    private String userName;
    private BigDecimal orderTotal;
    private long userId;
}
