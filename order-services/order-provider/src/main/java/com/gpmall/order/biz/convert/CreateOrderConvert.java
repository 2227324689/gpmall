package com.gpmall.order.biz.convert;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.order.biz.context.TransHandlerContext;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午11:04
 */
public class CreateOrderConvert implements TransConvert{


    @Override
    public TransHandlerContext convertRequest2Ctx(AbstractRequest req, TransHandlerContext context) {
        return null;
    }

    @Override
    public AbstractResponse convertCtx2Respond(TransHandlerContext ctx) {
        return null;
    }
}
