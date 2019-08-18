package com.gpmall.commons.lock.impl;

import com.gpmall.commons.lock.ApplicationContextUtils;
import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.tool.zookeeperConfig.CuratorFrameworkClient;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: jerry
 * @createDate: 20190814
 * @description:集成Zookeeper分布式锁之可重入互斥锁
 */
public class DistributedZooKeeperReentrantLock implements DistributedLock {

    public CuratorFrameworkClient curatorFrameworkClient;

    public ConcurrentHashMap<Thread,InterProcessMutex> locksMap = new ConcurrentHashMap();

    public DistributedZooKeeperReentrantLock(CuratorFrameworkClient curatorFrameworkClient) {
        this.curatorFrameworkClient = ApplicationContextUtils.getClass(CuratorFrameworkClient.class);
    }

    @Override
    public void lock(String path) throws Exception {
        if(locksMap.get(Thread.currentThread())==null){
            //互斥可重入锁，个人理解interProcessMutex 经济是一个锁的节点，path 对应的节点才是一个唯一的锁对象
            InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFrameworkClient.getZkCleint(),path);
            //一致等待获得锁,会在path 下创建一个临时有序接点
            interProcessMutex.acquire();

            locksMap.put(Thread.currentThread(),interProcessMutex);
        }else{
            InterProcessMutex interProcessMutex = locksMap.get(Thread.currentThread());
            //一致等待获得锁
            interProcessMutex.acquire();
        }

    }

    /**改实现是 InterProcessMutex 无限等待获得锁（内部实现是通过unit来实现无限等待），可以查看InterProcessMutex 的具体逻辑
     * 其实质等同于不带参数的--->interProcessMutex.acquire();
     * */
    @Override
    public boolean tryLock(String path) throws Exception {
        boolean theGetLock = tryLock(path,null,-1,-1);
        return theGetLock;
    }

    @Override
    public void lock(String path, TimeUnit unit, int waitTime) throws Exception {
       boolean theGetLock = tryLock(path,unit,waitTime,0);
    }

    /***
     *
     * @param path
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 对于ZK 来说次参数是无效的
     * @return
     * @throws Exception
     */
    @Override
    public boolean tryLock(String path, TimeUnit unit, int waitTime, int leaseTime) throws Exception {
        if(locksMap.get(Thread.currentThread())==null){
            //互斥可重入锁，个人理解interProcessMutex 经济是一个锁的节点，path 对应的节点才是一个唯一的锁对象
            InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFrameworkClient.getZkCleint(),path);
            //等待一个事件，如果没有获得锁会删除节点并且放回fasle,会在path 下创建一个临时有序接点
            boolean theGetLock =  interProcessMutex.acquire(waitTime,unit);
            if(theGetLock) {
                locksMap.put(Thread.currentThread(), interProcessMutex);
            }else{
                interProcessMutex = null;
            }
            return theGetLock;
        }else{
            InterProcessMutex interProcessMutex = locksMap.get(Thread.currentThread());
            //一致等待获得锁
            return interProcessMutex.acquire(waitTime,unit);
        }

    }

    @Override
    public void unlock(String path) {
        InterProcessMutex interProcessMutex = locksMap.get(Thread.currentThread());
        if(interProcessMutex!=null){
            try {
                interProcessMutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                locksMap.remove(Thread.currentThread());
            }
        }
    }
}
