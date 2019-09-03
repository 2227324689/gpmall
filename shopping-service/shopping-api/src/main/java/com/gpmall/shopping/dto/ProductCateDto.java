package com.gpmall.shopping.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by oahnus on 2019/8/8
 * 21:49.
 */
@Data
public class ProductCateDto implements Serializable {
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private String iconUrl;
}
