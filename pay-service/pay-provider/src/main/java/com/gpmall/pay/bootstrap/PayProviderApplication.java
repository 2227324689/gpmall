package com.gpmall.pay.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @decription 启动类
 * @author mic
 * @date 2019年8月8日 15:13:51
 */
@MapperScan(basePackages = "com.gpmall.pay.dal")
@ComponentScan(basePackages ={"com.gpmall.pay"})
@SpringBootApplication
public class PayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayProviderApplication.class, args);
    }

}
