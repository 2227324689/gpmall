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

    /**  后缀
     * @return
     */
    String lockSuffix() default "";

    /** 前缀
     * @return
     */
    String lockPrefix() default "";

    /** 分割符
     * @return
     */
    String separator() default "#";

    /**  实现类对应的名称 默认使用redis
     * @return
     */
    String lockType() default "";

    /**
     * 是否使用尝试锁。
     */
    boolean tryLock() default false;

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
