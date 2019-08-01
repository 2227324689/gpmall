package com.gpmall.order.biz.pipeline;/**
 * Created by mic on 2019/8/1.
 */

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:43
 */
public class SimplePipeline<Context,E extends Exception> extends BasePipeline<Handler<Context,E>> implements Pipeline<Context,E>{


    @Override
    public void handler(Context context) throws E {
        HandlerChain<Context,E> handlerChain=new SimpleHandlerChain<>(getHandlers());
        handlerChain.handleNext(context);
    }

    @Override
    public void handler(Context context, HandlerChain<Context, E> handlers) throws E {
        handler(context);
        handlers.handleNext(context);
    }
}
