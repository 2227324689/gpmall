package com.gpmall.comment.bootstrap;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author heps
 * @date 2019/8/11 22:42
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.gpmall.comment")
@MapperScan("com.gpmall.comment.dal")
public class CommentProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentProviderApplication.class, args);
    }
}
