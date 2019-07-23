package com.gpmall.shopping.dal.entitys;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PanelContent implements Serializable {
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

    private static final long serialVersionUID = 1L;

}