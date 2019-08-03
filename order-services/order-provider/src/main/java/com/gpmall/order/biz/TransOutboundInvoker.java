package com.gpmall.order.biz;

import com.gpmall.order.biz.context.TransHandlerContext;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午9:58
 */
public interface TransOutboundInvoker {

    /**
     * 启动流程.<br/>
     *
     */
    void start();

    /**
     * 终止流程.<br/>
     *
     */
    void shutdown();

    /**
     * 用于获取返回值<br/>
     *
     * @return
     */
    <T extends TransHandlerContext> T getContext();
}
