package com.gpmall.order.biz;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.order.biz.context.TransHandlerContext;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午10:03
 */
public interface HandlerItem {

    /**
     * 执行动作
     *
     * @param context
     * @return true表示继续执行，false则表示不用继续执行（直接返回给调用者）
     */
    boolean execute(TransHandlerContext context);

    /**
     * 执行动作
     *
     * @param context
     * @return true表示继续执行，false则表示不用继续执行（直接返回给调用者）
     */
    boolean execute(TransHandlerContext context,Exception e);

    /**
     *
     *
     * @param context
     */
    void afterExecute(TransHandlerContext context);
}
