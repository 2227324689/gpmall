package com.gpmall.pay.biz.abs;


import com.gpmall.commons.result.AbstractRequest;

/**
 * 数据验证接口类
 */
public interface Validator {

    void validate(AbstractRequest request);
}
