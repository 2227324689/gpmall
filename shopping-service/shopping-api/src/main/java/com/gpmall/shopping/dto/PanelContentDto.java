package com.gpmall.shopping.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-16:26
 */
@Data
public class PanelContentDto implements Serializable {

    private static final long serialVersionUID = -1584205373584476401L;
    private Integer id;

    private Integer panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;
}
