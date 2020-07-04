package com.gpmall.order.constants;/**
 * Created by mic on 2019/7/30.
 */

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-下午11:42
 */
public class OrderConstants {

    public static int ORDER_STATUS_INIT=0; //初始化状态
    public static int ORDER_STATUS_PAYED=1; //已支付
    public static int ORDER_STATUS_TRANSACTION_SUCCESS=4; //交易成功
    public static int ORDER_STATUS_TRANSACTION_CLOSE=5; //交易关闭
    public static int ORDER_STATUS_TRANSACTION_FAILED=6; //交易失败
    public static int ORDER_STATUS_TRANSACTION_CANCEL=7; //订单取消


    // 库存锁定状态 1库存已锁定 2库存已释放 3-库存减扣成功
    public static class OrderItemStockStatus {
        public static int ORDER_ITEM_STATUS_STOCK_LOCKED  = 1; // 库存已锁定
        public static int ORDER_ITEM_STATUS_STOCK_RELEASED = 2; // 库存已释放
        public static int ORDER_ITEM_STATUS_STOCK_DEDUCTION_SUCCESS = 3; // 库存减扣成功
    }
}
