package com.gpmall.commons.lock.impl.zk;

import com.gpmall.commons.lock.DistributedLockException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Description: zk分布式锁工厂
 * @date 2019年8月12日
 * @author msl 1015952139
 */
public class ZkMutexDistributedLockFactory {

    private static Logger logger = LoggerFactory.getLogger(ZkMutexDistributedLockFactory.class);


    protected final static String lockPath = "/gpcommons_lock/curator_recipes_lock/";
    protected static String projectName;

    static CuratorFramework client = null;


    static synchronized InterProcessMutex getInterProcessMutex(String lockKey) {
        if(client==null){
            init();
        }
        InterProcessMutex mutexLock = new InterProcessMutex(client, lockPath + projectName + lockKey);
        return mutexLock;
    }

    static synchronized CuratorFramework  getCuratorClient() throws DistributedLockException {
        if (client == null) {
            init();
        }
        return client;
    }


    /**
     * 初始化
     */
    private static synchronized void init() {
        if(client==null){
            //TODO zk地址
            String IPAndPort = "";
            //TODO 项目名
            String projectName = "";
            if(StringUtils.isEmpty(IPAndPort) || StringUtils.isEmpty(projectName)){
                logger.error("zk锁启动失败缺少配置--IP和端口号/项目名");
                throw new RuntimeException("zk锁启动异常--缺少配置--IP和端口号/项目名");
            }
            ZkMutexDistributedLockFactory.projectName = projectName+"/";
            client = CuratorFrameworkFactory.builder()
                    .connectString(IPAndPort)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
            client.start();
            // 启动后台线程
            LockBackGroundThread backGroundThread = new LockBackGroundThread(client);
            backGroundThread.start();
        }
    }

}
