package com.gpmall.order.dto;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午10:00
 */
@Data
public class OrderCountResponse extends AbstractResponse{

    private int count;
}
