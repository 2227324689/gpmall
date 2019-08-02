package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:36
 */
@Component
public class SimpleHandlerChain<Context,E extends Exception> extends BaseHandlerChain<Handler<Context,E>> implements HandlerChain<Context,E> {
    public SimpleHandlerChain(){

    }
    @Override
    public void handleNext(Context context) throws E {
        if (index < handlers.size()) {
            handlers.get(index++).handler(context, this);
        }
    }
}
