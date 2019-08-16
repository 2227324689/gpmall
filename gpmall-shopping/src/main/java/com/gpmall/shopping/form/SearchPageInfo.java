package com.gpmall.shopping.form;

import lombok.Data;

/**
 * @author lanwp
 * @Date 2019/8/14 11:36
 */
@Data
public class SearchPageInfo {
    private String key;
    private Integer page;
    private Integer size;
    private String sort;
    private Integer priceGt;
    private Integer priceLte;
}
