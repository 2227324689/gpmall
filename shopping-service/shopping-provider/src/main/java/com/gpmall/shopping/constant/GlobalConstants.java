package com.gpmall.shopping.constant;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-16:34
 */
public class GlobalConstants {

    public final static String HEADER_PANEL_CACHE_KEY="header_panel"; //导航栏板块缓存
    public final static int HEADER_PANEL_ID=0;//导航栏板块表id

    public final static int RECOMMEND_PANEL_ID=6;//推荐商品板块表id
    public final static String RECOMMEND_PANEL_CACHE_KEY="recommend_panel";//推荐商品板块缓存key

    public final static String PRODUCT_ITEM_CACHE_KEY="product_item";//商品详情缓存前缀
    public final static int PRODUCT_ITEM_EXPIRE_TIME=60*60*1; //过期时间

}
