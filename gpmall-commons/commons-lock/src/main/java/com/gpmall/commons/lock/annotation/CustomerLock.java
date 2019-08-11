package com.gpmall.commons.lock.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: zm
 * @createDate: 2018/6/23$ 11:02$
 * @updateRemark: 修改内容
 * @description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerLock {

    /**
     * lock key
     * eg  #arg.id
     *
     * @return
     */
    String lockKey();

    String lockSuffix() default "";

    String lockPrefix() default "com/gpmall/commons/lock";

    String separator() default "#";

    String lockType() default "";

    /**
     * 是否使用尝试锁。
     */
    boolean tryLock() default true;

    /**
     * 最长等待时间。
     * 该字段只有当tryLock()返回true才有效。
     */
    int waitTime() default 0;

    /**
     * 锁超时时间。
     * 超时时间过后，锁自动释放。
     * 建议：
     * 尽量缩简需要加锁的逻辑。
     */
    int leaseTime() default 30;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
