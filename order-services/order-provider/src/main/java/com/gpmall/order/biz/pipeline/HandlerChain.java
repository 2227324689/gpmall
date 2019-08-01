package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:28
 */
public interface HandlerChain<Context,E extends Exception> {

    void handleNext(Context context) throws E;
}
