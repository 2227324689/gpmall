package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.order.biz.context.TransHandlerContext;
import com.gpmall.order.constant.OrderRetCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午10:37
 */
@Slf4j
public class DefaultTransPipeline implements TransPipeline{


    private TransHandlerNode tail;

    private TransHandlerNode head = new TransHandlerNode();

    private TransHandlerContext context = null;

    public DefaultTransPipeline(TransHandlerContext context) {
        setContext(context);
        tail = head;
    }

    @Override
    public void addFirst(TransHandler... handlers) {
        TransHandlerNode pre = head.getNext();
        for (TransHandler handler : handlers) {
            if(handler == null) {
                continue;
            }
            TransHandlerNode node = new TransHandlerNode();
            node.setHandler(handler);
            node.setNext(pre);

            pre = node;
        }

        head.setNext(pre);
    }

    @Override
    public void addLast(TransHandler... handlers) {
        TransHandlerNode next = tail;
        for (TransHandler handler : handlers) {
            if(handler == null) {
                continue;
            }

            TransHandlerNode node = new TransHandlerNode();
            node.setHandler(handler);
            next.setNext(node);
            next = node;
        }

        tail = next;
    }

    @Override
    public void start() {
        try {
            head.getNext().exec(getContext());
        } catch (Exception ex) {
            log.error("pipeline系统运行异常.", ex);
            throw ex;
        }
    }

    @Override
    public void shutdown() {
        ;
    }

    @Override
    public <T extends TransHandlerContext> T getContext() {
        return (T)context;
    }

    public void setContext(TransHandlerContext context) {
        this.context = context;
    }
}
