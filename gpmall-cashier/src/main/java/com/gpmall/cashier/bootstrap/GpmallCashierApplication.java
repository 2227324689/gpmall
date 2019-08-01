package com.gpmall.cashier.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gpmall.cashier")
@SpringBootApplication
public class GpmallCashierApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpmallCashierApplication.class, args);
    }

}
