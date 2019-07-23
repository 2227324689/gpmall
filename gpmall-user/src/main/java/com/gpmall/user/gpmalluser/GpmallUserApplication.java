package com.gpmall.user.gpmalluser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gpmall.user")
@SpringBootApplication
public class GpmallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpmallUserApplication.class, args);
    }

}
