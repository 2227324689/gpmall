package com.gpmall.search.dto;


import com.gpmall.search.consts.SearchEnum;

import java.io.Serializable;

/**
 * 通用响应类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public class SearchResponse implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public static SearchResponse ok() {
        SearchResponse response = new SearchResponse();
        response.setCode(SearchEnum.SUCCESS.getCode());
        return response;
    }

    public static SearchResponse err() {
        SearchResponse response = new SearchResponse();
        response.setCode(SearchEnum.FAILED.getCode());
        return response;
    }

    public SearchResponse msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public SearchResponse data(Object data) {
        this.setData(data);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
