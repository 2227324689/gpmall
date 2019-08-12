package com.gpmall.search;

import com.gpmall.commons.tool.exception.BaseBusinessException;

/**
 * 搜索模块异常处理
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public class SearchException extends BaseBusinessException {
    public SearchException() {
    }

    public SearchException(String errorCode) {
        super(errorCode);
    }

    public SearchException(String errorCode, String message) {
        super(errorCode, message);
    }

    public static SearchException create(String errorCode, String message) {
        return new SearchException(errorCode, message);
    }
}
