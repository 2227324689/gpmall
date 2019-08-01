package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:39
 */
public abstract class BasePipeline<Handler> {

    private List<Handler> handlers;

    public BasePipeline(int initSize) {
        handlers = new ArrayList<>(initSize);
    }

    public BasePipeline() {
        this(2);
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public void removeValve(Handler handler) {
        handlers.remove(handler);
    }

}
