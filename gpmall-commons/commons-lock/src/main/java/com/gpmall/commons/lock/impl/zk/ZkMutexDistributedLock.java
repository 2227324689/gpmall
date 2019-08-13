package com.gpmall.commons.lock.impl.zk;

import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.DistributedLockException;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * Description: zk分布式锁实现,
 * <p1>注意:此锁可以重入，但是重入几次需要释放几次。</p1>
 * @date 2019年8月12日
 * @author msl 1015952139
 */
public class ZkMutexDistributedLock implements DistributedLock {
    private InterProcessMutex interProcessMutex;
    private int defaultTimeoutSeconds = 30;
    public ZkMutexDistributedLock() {
        interProcessMutex = ZkMutexDistributedLockFactory.getInterProcessMutex();
    }

    @Override
    public void lock(String key) throws DistributedLockException {
        try{
            interProcessMutex.acquire();
        } catch (Exception e){
            throw new DistributedLockException("zk加锁异常: ", e);
        }
    }

    @Override
    public boolean tryLock(String key) throws DistributedLockException {
        try {
            return interProcessMutex.acquire(defaultTimeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new DistributedLockException("zk加锁异常: ", e);
        }
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) throws DistributedLockException {
        try{
            interProcessMutex.acquire(timeout, unit);
        } catch (Exception e){
            throw new DistributedLockException("zk加锁异常: ", e);
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributedLockException {
        try {
            return interProcessMutex.acquire(waitTime, unit);
        } catch (Exception e) {
            throw new DistributedLockException("zk加锁异常: ", e);
        }
    }

    @Override
    public void unlock(String lockKey) throws DistributedLockException {
        try {
            interProcessMutex.release();
        } catch (Exception e) {
            throw new DistributedLockException("zk释放锁异常: ", e);
        }
    }
}
