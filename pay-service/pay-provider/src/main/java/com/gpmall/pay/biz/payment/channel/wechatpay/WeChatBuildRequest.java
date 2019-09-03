package com.gpmall.pay.biz.payment.channel.wechatpay;

import com.gpmall.pay.biz.payment.commons.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Slf4j
public class WeChatBuildRequest {



    /**
     * 随机字符串，不长于32位。
     *
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Utils.GetMD5Code(String.valueOf(random.nextInt(10000)));
    }

    /**
     * 时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 微信支付签名算法sign
     *
     * @param key        商户的appsecret
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String createSign(SortedMap<Object, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        // 所有参与传参的参数按照accsii排序（升序）
        parameters.forEach((k,v) ->{
            if (!"sign".equals(k) && !"key".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        });
        sb.append("key=" + key);
        String sign = MD5Utils.GetMD5Code(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 解析xml字符串转成map集合
     *
     * @param xml
     * @return
     */
    public static Map<String, String> doXMLParse(String xml) {
        Map<String, String> map = new HashMap<>(12);
        // 将编码改为UTF-8,并去掉换行符\空格等
        xml = xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        //去掉空白 换行符
        final StringBuilder sb = new StringBuilder(xml.length());
        char c;
        for (int i = 0; i < xml.length(); i++) {
            c = xml.charAt(i);
            if (c != '\n' && c != '\r' && c != ' ') {
                sb.append(c);
            }
        }
        xml = sb.toString();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            StringReader reader = new StringReader(xml);
            InputSource inputSource = new InputSource(reader);
            Document document = documentBuilder.parse(inputSource);
            // 1.获取xml文件的根元素
            Element element = document.getDocumentElement();
            // 2.获取根元素下的所有子标签
            NodeList nodeList = element.getChildNodes();
            // 3.遍历子标签集合
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                map.put(node.getNodeName(), node.getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            log.error("xml解析异常：" + e);
        }

        return map;
    }

    /**
     * @param return_code 返回编码
     * @param return_msg  返回信息
     * @return
     * @Description：返回给微信的参数
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /**
     * @param parameters 请求参数
     * @return
     * @Description：将请求参数转换为xml格式的string
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        parameters.forEach((k,v) -> {
            if ("attach".equalsIgnoreCase((String) k) || "body".equalsIgnoreCase((String) k)
                    || "sign".equalsIgnoreCase((String) k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        });
        sb.append("</xml>");
        try {
            return sb.toString();
        } catch (Exception e) {
            log.error("map转化成xml异常：" + e);
        }
        return "";
    }
}
