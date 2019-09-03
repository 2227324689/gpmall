package com.gpmall.pay.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @decription 启动类
 * @author mic
 * @date 2019年8月8日 15:13:51
 */
@ComponentScan(basePackages ={"com.gpmall.pay"})
@MapperScan(basePackages = "com.gpmall.pay.dal")
@SpringBootApplication
public class PayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayProviderApplication.class, args);
    }

}
