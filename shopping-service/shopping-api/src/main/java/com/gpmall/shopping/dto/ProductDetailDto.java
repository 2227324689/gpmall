package com.gpmall.shopping.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-18:08
 */
@Data
public class ProductDetailDto implements Serializable {

    private static final long serialVersionUID = -597050593951733519L;
    private Long productId;

    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    private Long limitNum;

    private String productImageBig;

    private String detail;

    private List<String> productImageSmall;
}
