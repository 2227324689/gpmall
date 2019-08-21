package com.gpmall.commons.tool.zookeeperConfig;

import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.utils.ZookeeperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Configuration
@EnableConfigurationProperties(value = ZooKeeperClientProperties.class)
@ConditionalOnClass(value = CuratorFrameworkFactory.class)
public class ZookeeperAutoConfiguration {
    public static final Logger logger = LoggerFactory.getLogger(CuratorFrameworkFactory.class);

    @Autowired
    ZooKeeperClientProperties zooKeeperClientProperties;

    @Bean
    public CuratorFrameworkClient createCuratorFrameworkClient(){
        logger.info(zooKeeperClientProperties.toString());
        return  new CuratorFrameworkClient(zooKeeperClientProperties);
    }
}
