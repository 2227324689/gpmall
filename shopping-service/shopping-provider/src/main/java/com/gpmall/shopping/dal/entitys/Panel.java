package com.gpmall.shopping.dal.entitys;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Panel implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private Integer status;

    private String remark;

    private Date created;

    private Date updated;

    private List<PanelContentItem> panelContentItems;

    private static final long serialVersionUID = 1L;

}