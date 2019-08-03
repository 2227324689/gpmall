package com.gpmall.order.biz;

import com.gpmall.order.biz.callback.TransCallback;
import com.gpmall.order.biz.context.TransHandlerContext;
import com.gpmall.order.biz.handler.AbstractTransHandler;
import com.gpmall.order.biz.handler.TransHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransHandlerNode {

    private TransHandler handler;

    private TransHandlerNode next = null;

    public void exec(TransHandlerContext context) {
        AbstractTransHandler transHandler = (AbstractTransHandler) handler;


        //前置回调函数
        execCallbacks(transHandler.getCallback(), context, null);

        boolean succ = handler.handle(context);

        //后置回调函数
        execCallbacks(transHandler.getCallback(), context, null);

        if (next != null && succ) {
            next.exec(context);
        }

    }

    private void execCallbacks(TransCallback callback
            , TransHandlerContext context, Throwable ex) {
        try {
            if (ex == null) {
                callback.onDone(context);
            }
        } catch (Exception e) {
            log.warn("Pipeline回调处理异常.", e);
        }
    }

    public TransHandler getHandler() {
        return handler;
    }

    public void setHandler(TransHandler handler) {
        this.handler = handler;
    }

    public TransHandlerNode getNext() {
        return next;
    }

    public void setNext(TransHandlerNode next) {
        this.next = next;
    }
}
