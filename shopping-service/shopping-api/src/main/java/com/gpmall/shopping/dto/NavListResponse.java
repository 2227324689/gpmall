package com.gpmall.shopping.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-16:27
 */
@Data
public class NavListResponse extends AbstractResponse {

    private List<PanelContentDto> pannelContentDtos;
}
