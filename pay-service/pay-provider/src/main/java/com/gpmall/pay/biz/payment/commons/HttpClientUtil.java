package com.gpmall.pay.biz.payment.commons;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

/**
 * @author
 */
public class HttpClientUtil {

    static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;

    private static CloseableHttpClient httpClient;

    private static RequestConfig requestConfig;

    static {
        try {
            SSLContextBuilder builder = SSLContextBuilder.create();
            builder.loadTrustMaterial(null, (chain, authType) -> true);
            SSLContext sslContext = builder.build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext, (s, sslSession) -> true);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf).build();
            connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connMgr.setMaxTotal(200);
            connMgr.setDefaultMaxPerRoute(20);
            requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
            httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } catch (Exception e) {
            logger.error("init httpclient exception:" + e);
        }
    }

    /**
     * 不需要参数
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpGet(String url) throws Exception {
        HttpGet httpGet = null;
        HttpEntity httpEntity = null;
        String responseBody = "";
        try {
            httpGet = new HttpGet(url);
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = httpClient.execute(httpGet, responseHandler);
            return responseBody;
        } finally {
            try {
                if (httpEntity != null) {
                    httpEntity.getContent().close();
                }
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
            } catch (Exception e) {
                logger.error("关闭HTTP链接异常", e);
            }
            logger.info("req:{},resp:{}", url, responseBody);
        }
    }

    /**
     * 发送 Post请求
     * 参数为string
     * @param url
     * @param data
     * @return
     */
    public static String httpPost(String url, String data) {
        logger.info("开始发送post请求：url:{},data:{}", url, data);
        HttpPost httpPost = null;
        String responseBody = "";
        try {
            httpPost = new HttpPost(url);
            HttpEntity httpEntity = new StringEntity(data, "UTF-8");
            httpPost.setEntity(httpEntity);
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = httpClient.execute(httpPost, responseHandler);
            return responseBody;
        } catch (Exception e) {
            logger.error("发送post请求异常：" + e);
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        logger.info("req:{},resp:{}", url, responseBody);
        return null;
    }
}
