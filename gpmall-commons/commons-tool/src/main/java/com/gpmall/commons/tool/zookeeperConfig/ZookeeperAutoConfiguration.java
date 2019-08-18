package com.gpmall.commons.tool.zookeeperConfig;

import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.utils.ZookeeperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;


//@Component(value = "com.gpmall.commons.tool.zookeeperConfig.*")
//@Import(value = ZookeeperClientProperties.class)

@Configuration
@EnableConfigurationProperties(value = ZooKeeperClientProperties.class)
@ConditionalOnClass(value = CuratorFrameworkFactory.class)
public class ZookeeperAutoConfiguration {
    @Autowired
    ZooKeeperClientProperties zooKeeperClientProperties;

    @Bean
    public CuratorFrameworkClient createCuratorFrameworkClient(){
            return  new CuratorFrameworkClient(zooKeeperClientProperties);
    }
}
