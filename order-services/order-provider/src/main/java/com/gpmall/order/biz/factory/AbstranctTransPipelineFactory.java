package com.gpmall.order.biz.factory;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.order.biz.handler.DefaultTransPipeline;
import com.gpmall.order.biz.handler.TransPipeline;
import com.gpmall.order.biz.context.AbsTransHandlerContext;
import com.gpmall.order.biz.context.TransHandlerContext;
import com.gpmall.order.biz.TransOutboundInvoker;
import com.gpmall.order.biz.convert.TransConvert;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午10:30
 */
public abstract class AbstranctTransPipelineFactory <T extends AbstractRequest> implements TransPipelineFactory<T>{

    @Override
    public final TransOutboundInvoker build(T obj) {
        //创建转换器
        TransConvert convert = createConvert();
        //创建上下文
        TransHandlerContext context = createContext();
        //上朔
        AbsTransHandlerContext absCtx = (AbsTransHandlerContext) context;

        //set转换器
        absCtx.setConvert(convert);
        //上下文转换
        convert.convertRequest2Ctx(obj, context);
        //创建管道
        TransPipeline pipeline = createPipeline(context);
        //build管道
        doBuild(pipeline);
        //返回
        return pipeline;
    }

    protected abstract TransHandlerContext createContext();

    protected abstract void doBuild(TransPipeline pipeline);

    protected TransPipeline createPipeline(TransHandlerContext context) {
        return new DefaultTransPipeline(context);
    }

    protected abstract TransConvert createConvert();
}
