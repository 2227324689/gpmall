package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:33
 *
 * handler chain抽象类
 */
public abstract class BaseHandlerChain<Handler> {

    @Autowired
    protected List<Handler> handlers;

    protected int index = 0;

    protected int size;

    public BaseHandlerChain(){}

    public BaseHandlerChain(List<Handler> handlers) {
        this.handlers = handlers;
        this.size = handlers.size();
    }

    public BaseHandlerChain(List<Handler> valves, int index) {
        this(valves);
        this.index = index;
    }
}
