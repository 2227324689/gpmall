package com.gpmall.shopping.gpmallshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gpmall.shopping")
@SpringBootApplication
public class GpmallShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpmallShoppingApplication.class, args);
    }

}
