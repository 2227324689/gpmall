package com.gpmall.user.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gpmall.user")
@MapperScan("com.gpmall.user.dal")
public class UserProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserProviderApplication.class, args);
	}

}
