package com.gpmall.commons.lock;

/**
 * Description: 分布式锁异常
 * @date 2019年8月12日
 * @author msl 1015952139
 */
public class DistributedLockException extends Exception{

    private static final long serialVersionUID = -4422354332440610539L;

    public DistributedLockException() {
        super();
    }

    public DistributedLockException(String message) {
        super(message);
    }

    public DistributedLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistributedLockException(Throwable cause) {
        super(cause);
    }

    protected DistributedLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
