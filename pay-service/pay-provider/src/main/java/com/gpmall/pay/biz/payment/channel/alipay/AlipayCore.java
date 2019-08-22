package com.gpmall.pay.biz.payment.channel.alipay;

import java.util.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
public class AlipayCore {

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, Object> paraFilter(Map<String, Object> sArray) {

        Map<String, Object> result = new TreeMap<>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key).toString();
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer prestr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key).toString();
            //拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
            	prestr.append(key + "=" + value);
            } else {
            	prestr.append(key + "=" + value + "&");
            }
        }

        return prestr.toString();
    }
    
    
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createAlipayMobileLinkString(Map<String, Object> params) {

    	List<String> keys = new ArrayList<String>(params.keySet());
       // Collections.sort(keys);

        StringBuffer prestr = new StringBuffer();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key).toString();
          /*  if(key.equals("sign")){
            	continue;
            }*/
            //拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
            	prestr.append(key + "=\"" + value + "\"");
            } else {
            	prestr.append(key + "=\"" + value + "\"&");
            }
        }

        return prestr.toString();
    }
    
}
