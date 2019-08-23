package com.gpmall.shopping.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanwp
 * @Date 2019/8/14 11:36
 */
@Data
@ApiModel
public class SearchPageInfo {

    @ApiModelProperty(name = "key", value = "关键字")
    private String key;

    @ApiModelProperty(name = "page", value = "当前页", example = "1")
    private Integer page;

    @ApiModelProperty(name = "size", value = "每页条数", example = "10")
    private Integer size;

    @ApiModelProperty(name = "sort", value = "排序")
    private String sort;

    @ApiModelProperty(name = "priceGt", value = "大于x", example = "1")
    private Integer priceGt;

    @ApiModelProperty(name = "priceLte", value = "小于x", example = "10")
    private Integer priceLte;
}
