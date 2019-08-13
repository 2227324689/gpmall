package com.gpmall.search.bootstrap;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 搜索服务启动类
 *
 * @author jin
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.gpmall.search.repository")
@MapperScan(basePackages = "com.gpmall.search.mapper")
public class SearchProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchProviderApplication.class, args);
	}

}

