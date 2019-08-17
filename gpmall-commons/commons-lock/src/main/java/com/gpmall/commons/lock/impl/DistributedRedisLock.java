package com.gpmall.commons.lock.impl;

import com.gpmall.commons.lock.ApplicationContextUtils;
import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.DistributedLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author: zm
 * @createDate: 2018/6/23$ 10:16$
 * @updateRemark: 修改内容
 * @description:
 */
public class DistributedRedisLock implements DistributedLock {

    private RedissonClient redissonClient;


    public DistributedRedisLock() {
        this.redissonClient = ApplicationContextUtils.getClass(RedissonClient.class);
    }


    /**
     * 默认超时时间30s
     *
     * @param key
     */
    @Override
    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    /**
     * 未获取到锁直接返回false
     *
     * @param key
     * @return
     */
    @Override
    public boolean tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, unit);
    }

    /**
     * 尝试获取锁(会转变为同步锁)
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @throws DistributedLockException
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributedLockException {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            throw new DistributedLockException("redis加锁异常: ", e);
        }
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
