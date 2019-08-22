package com.gpmall.commons.tool.zookeeperConfig;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Administrator
 * 2019/8/14 0014
 * 18:30
 */
public class CuratorFrameworkClient {

    ZooKeeperClientProperties zooKeeperClientProperties;

    CuratorFramework curatorFramework = null;

    public CuratorFrameworkClient(){}

    public CuratorFrameworkClient(ZooKeeperClientProperties zooKeeperClientProperties) {
        this.zooKeeperClientProperties = zooKeeperClientProperties;
    }

    public CuratorFramework getZkCleint(){
        if(curatorFramework == null ){
            return createCuratorFramework();
        }else{
            if(zooKeeperClientProperties.isSingleton()){
                return curatorFramework;
            }else{
                return createCuratorFramework();
            }
        }
    }
    public  CuratorFramework createCuratorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,100,2000);
        curatorFramework  = (CuratorFramework) CuratorFrameworkFactory.builder().
                connectString(zooKeeperClientProperties.getZkHosts()).
                sessionTimeoutMs(zooKeeperClientProperties.getSessionTimeout()).
                namespace(zooKeeperClientProperties.getNamespace()).
                retryPolicy(retryPolicy);
        curatorFramework.start();
        return curatorFramework;
    }
}
