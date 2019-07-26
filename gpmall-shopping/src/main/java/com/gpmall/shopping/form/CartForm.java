package com.gpmall.shopping.form;/**
 * Created by mic on 2019/7/26.
 */

import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/26-下午4:50
 */
@Data
public class CartForm {


    private Long userId;

    private Long productId;

    private String checked;

    private Integer productNum;


}
