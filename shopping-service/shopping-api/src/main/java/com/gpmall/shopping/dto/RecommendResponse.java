package com.gpmall.shopping.dto;/**
 * Created by mic on 2019/7/29.
 */

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.Set;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/29-下午11:10
 */
@Data
public class RecommendResponse extends AbstractResponse{

    private Set<PanelDto> panelContentItemDtos;

}
