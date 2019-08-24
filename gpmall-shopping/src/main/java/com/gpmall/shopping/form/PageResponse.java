package com.gpmall.shopping.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/29-下午10:49
 */
@Data
@ApiModel
public class PageResponse {

    @ApiModelProperty(name = "data", value = "数据信息")
    private Object data;

    @ApiModelProperty(name = "total", value = "数据条数", example = "10")
    private Long total;
}
