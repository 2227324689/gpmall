package com.gpmall.commons.tool.exception;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/22-14:58
 * 业务处理异常()
 */
public class ProcessException extends BaseBusinessException {
    public ProcessException() {
        super();
    }

    public ProcessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ProcessException(Throwable arg0) {
        super(arg0);
    }

    public ProcessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ProcessException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ProcessException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
