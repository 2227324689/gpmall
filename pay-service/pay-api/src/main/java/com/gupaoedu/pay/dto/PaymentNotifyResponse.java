package com.gupaoedu.pay.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Data
public class PaymentNotifyResponse extends AbstractResponse{
    /** 返回给服务端的执行结果的报文 */
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PaymentNotifyResponse{" +
                "result='" + result + '\'' +
                "} " + super.toString();
    }
}
