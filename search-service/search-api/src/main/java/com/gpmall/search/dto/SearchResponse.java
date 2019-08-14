package com.gpmall.search.dto;


import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.search.consts.SearchRetCode;
import lombok.Data;

/**
 * 通用响应类
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
@Data
public class SearchResponse extends AbstractResponse {

    private Object data;

    public Object getData() {
        return data;
    }

    public SearchResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public SearchResponse ok(Object data) {
        this.setCode(SearchRetCode.SUCCESS.getCode());
        this.setMsg(SearchRetCode.SUCCESS.getMsg());
        return this;
    }
}
