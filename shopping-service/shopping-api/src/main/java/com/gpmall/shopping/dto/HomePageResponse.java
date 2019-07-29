package com.gpmall.shopping.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-17:48
 */
@Data
public class HomePageResponse extends AbstractResponse {

    private Set<PanelDto> panelContentItemDtos;
}
