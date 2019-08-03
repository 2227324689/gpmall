package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.order.biz.TransOutboundInvoker;
import com.gpmall.order.biz.handler.TransHandler;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午9:58
 */
public interface TransPipeline extends TransOutboundInvoker {

    /**
     *
     * @param handlers
     */
    void addFirst(TransHandler... handlers);

    /**
     *
     * @param handlers
     */
    void addLast(TransHandler ... handlers);
}
