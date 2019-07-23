
package com.gpmall.commons.tool.exception;


import com.gpmall.commons.result.AbstractResponse;

public class ExceptionUtil {

    /**
     * 将下层抛出的异常转换为resp返回码
     *
     * @param e Exception
     * @return
     */
    public static AbstractResponse handlerException4biz(AbstractResponse response,Exception e) throws Exception {
        Exception ex = null;
        if (!(e instanceof Exception)) {
            return null;
        }
        if (e instanceof ValidateException) {
            response.setCode(((ValidateException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if(e instanceof ProcessException) {
            response.setCode(((ProcessException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if(e instanceof BizException) {
            response.setCode(((BizException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if (e instanceof Exception) {
            throw e; //处理不了，抛出去调用方处理
        }
        return response;
    }
}
