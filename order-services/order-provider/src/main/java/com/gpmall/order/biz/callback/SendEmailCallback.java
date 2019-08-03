package com.gpmall.order.biz.callback;/**
 * Created by mic on 2019/8/3.
 */

import com.gpmall.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/3-上午10:30
 */
@Slf4j
@Component
public class SendEmailCallback implements TransCallback{

    @Override
    public void onDone(TransHandlerContext context) {
        log.info("订单下单成功后，会发送邮件");
    }
}
