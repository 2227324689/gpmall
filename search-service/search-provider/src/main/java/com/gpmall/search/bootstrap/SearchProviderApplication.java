package com.gpmall.search.bootstrap;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@MapperScan(basePackages = "com.gpmall.search.mapper")
@SpringBootApplication
public class SearchProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchProviderApplication.class, args);
    }

}
