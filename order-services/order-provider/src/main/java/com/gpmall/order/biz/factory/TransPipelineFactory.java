package com.gpmall.order.biz.factory;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.order.biz.TransOutboundInvoker;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午10:28
 */
public interface TransPipelineFactory<T> {

    TransOutboundInvoker build(T obj);
}
