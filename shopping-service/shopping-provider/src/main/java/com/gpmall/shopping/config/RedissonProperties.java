package com.gpmall.shopping.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mic on 2019/7/23.
 */
@ConfigurationProperties(prefix = "spring.redis",ignoreUnknownFields = false)
@Data
public class RedissonProperties {

    private String address; //连接地址

    private int database;

    /**
     * 等待节点回复命令的时间。该时间从命令发送成功时开始计时
     */
    private int timeout;

    private String password;

    private RedissonPoolProperties pool;
}
