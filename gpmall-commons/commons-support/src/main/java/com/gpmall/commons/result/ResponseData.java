package com.gpmall.commons.result;

public class ResponseData<T> {

    private int code;  //代码

    private String message; //消息

    private T data; //返回的数据

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
