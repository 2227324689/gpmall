package com.gpmall.commons.tool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 全局交易单号生成工具
 */
public class TradeNoUtils {

    /**
     * 生成支付流水号
     *
     * @return
     */
    public static String generateTradeNo() {
        Date now = new Date();
        String date = new SimpleDateFormat("yyyyMMdd").format(now);
        String seconds = new SimpleDateFormat("HHmmss").format(now);
        String millSeconds=new SimpleDateFormat("SSS").format(now);
        return date + "00001000" + getTwo() + "00" + seconds + getTwo()+millSeconds;

    }

    /**
     * 产生随机的2位数
     *
     * @return
     */
    public static String getTwo() {
        Random rad = new Random();

        String result = rad.nextInt(100) + "";

        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;

    }

    public static void main(String[] args) {
        System.out.println(generateTradeNo());
    }
}
