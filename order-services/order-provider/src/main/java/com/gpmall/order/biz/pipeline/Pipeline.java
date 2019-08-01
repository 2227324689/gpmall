package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:26
 * 基础管道
 */
public interface Pipeline<Context,E extends Exception> extends Handler<Context,E>{

    void handler(Context context) throws E;

}
