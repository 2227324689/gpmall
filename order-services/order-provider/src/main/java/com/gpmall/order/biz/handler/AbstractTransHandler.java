package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/2.
 */

import com.gpmall.order.biz.callback.TransCallback;
import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-下午10:01
 */
@Data
public abstract class AbstractTransHandler implements TransHandler {

    public static final String DEFAULT = "default";

    public TransCallback getTransCallback(){return null;}
}
