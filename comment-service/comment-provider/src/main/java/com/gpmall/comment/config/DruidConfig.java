package com.gpmall.comment.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by mic on 2019/7/22.
 */

@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }
}
