package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.order.biz.callback.TransCallback;
import com.gpmall.order.biz.context.TransHandlerContext;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午9:52
 */
public interface TransHandler {


    /**
     * 是否采用异步方式执行
     * @return
     */
    boolean isAsync();

    /**
     * 执行交易具体业务<br/>
     *
     * @param context
     * @return true则继续执行下一个Handler，否则结束Handler Chain的执行直接返回<br/>
     */
    boolean handle(TransHandlerContext context);


}
