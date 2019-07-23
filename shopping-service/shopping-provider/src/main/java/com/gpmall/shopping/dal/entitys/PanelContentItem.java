package com.gpmall.shopping.dal.entitys;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-18:03
 */
@Data
public class PanelContentItem {

    private Integer id;

    private Integer panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

    private Date created;

    private Date updated;

    private String productName;

    private BigDecimal salePrice;

    private String subTitle;
}
