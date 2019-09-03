package com.gpmall.pay.biz.abs;


import com.gpmall.commons.result.AbstractRequest;

/**
 * 数据验证接口类
 * @author
 */
public interface Validator {
    /**
     * 数据验证
     * @param request
     */
    void validate(AbstractRequest request);
}
