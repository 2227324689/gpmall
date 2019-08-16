package com.gpmall.comment;

import com.gpmall.commons.tool.exception.BaseBusinessException;

/**
 * @author heps
 * @date 2019/8/12 21:26
 */
public class CommentException extends BaseBusinessException {

    public CommentException() {
    }

    public CommentException(String errorCode) {
        super(errorCode);
    }

    public CommentException(String errorCode, String message) {
        super(errorCode, message);
    }
}
