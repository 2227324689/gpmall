package com.gpmall.commons.lock.impl;

import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.DistributedLockException;

import java.util.concurrent.TimeUnit;

/**
 * waapper
 */
public class DistributedLockWrapper implements DistributedLock {

    private DistributedLock distributedLock;

    public DistributedLockWrapper(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    @Override
    public void lock(String key) throws DistributedLockException {
        distributedLock.lock(key);
    }

    @Override
    public boolean tryLock(String key) throws DistributedLockException {
        return distributedLock.tryLock(key);
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) throws DistributedLockException {
        distributedLock.lock(lockKey, unit, timeout);
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributedLockException {
        return distributedLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }

    @Override
    public void unlock(String lockKey) throws DistributedLockException {
        distributedLock.unlock(lockKey);
    }
}
