package com.gpmall.sso.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gpmall.sso")
public class GpmallSsoApplication {

    public static void main(String[] args) {

        SpringApplication.run(GpmallSsoApplication.class, args);
    }

}
