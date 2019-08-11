package com.gpmall.search.bootstrap;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * 启动类
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.gpmall.search.repository")
@MapperScan(basePackages = "com.gpmall.search.mapper")
public class SearchProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchProviderApplication.class, args);
    }

}

