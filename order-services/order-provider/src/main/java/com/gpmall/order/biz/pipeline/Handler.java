package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:31
 */
public interface Handler<Context,E extends Exception> {

    /**
     * 业务类接口，所有需要扩展的业务都必须实现这个接口
     * @param context
     * @param handlers
     */
    void handler(Context context,HandlerChain<Context,E> handlers) throws E;
}
