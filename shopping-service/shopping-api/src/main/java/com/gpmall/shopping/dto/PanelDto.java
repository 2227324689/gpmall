package com.gpmall.shopping.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-18:32
 */
@Data
public class PanelDto implements Serializable {
    private static final long serialVersionUID = -9099372701554072936L;
    private Integer id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private Integer status;

    private String remark;

    private List<PanelContentItemDto> panelContentItems;
}
