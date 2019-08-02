package com.gpmall.order.biz.context;

import com.gpmall.order.biz.convert.TransConvert;
import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午9:55
 */
@Data
public abstract class AbsTransHandlerContext implements TransHandlerContext {

    private TransConvert convert = null;


}
