package com.gpmall.cashier.bootstrap.form;/**
 * Created by mic on 2019/8/9.
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/9-下午4:24
 */
@Data
public class PayForm {

    private String nickName;
    private BigDecimal money;
    private String info;
    private String orderId;
    private String payType;
}
