package com.gpmall.order.biz;

import com.gpmall.order.biz.context.TransHandlerContext;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午9:52
 *
 * 交易回调
 */
public interface TransCallback {

    void onDone(TransHandlerContext context);
}
