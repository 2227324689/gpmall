package com.gpmall.shopping.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.gpmall.shopping.dal")
@ComponentScan(basePackages = "com.gpmall.shopping")
@SpringBootApplication
public class ShoppingProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingProviderApplication.class, args);
    }

}
