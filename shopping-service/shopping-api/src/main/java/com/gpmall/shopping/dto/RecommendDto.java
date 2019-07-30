package com.gpmall.shopping.dto;/**
 * Created by mic on 2019/7/29.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/29-下午11:14
 */
@Data
public class RecommendDto implements Serializable{

    private Integer id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private String remark;




}
