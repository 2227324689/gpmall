package com.gpmall.shopping.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置Swagger2。
 * Created by ZZZ on 2019/2/18.
 */
@Setter
@Configuration
@EnableSwagger2
@ConfigurationProperties("swagger")
public class Swagger2 {

    private boolean enable;
    // 不配置取默认值
    private String host;

    private static final String SCHEMA_NAME = "Authorization";
    private static final String KEY_NAME = "access_token";
    private static final String PASS_AS = "header";


    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList();
        apiKeyList.add(new ApiKey(SCHEMA_NAME, KEY_NAME, PASS_AS));
        return apiKeyList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList() {{
            add(new SecurityReference("Authorization", authorizationScopes));
        }};
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList() {{
            add(SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.any())
                    .build());
        }};
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .host(host)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gpmall.shopping.controller"))
                .paths(PathSelectors.any())
                .build()
                .enable(enable)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GPMall-Shopping的API接口")
                .description("GPMall-Shopping")
                .version("1.0")
                .build();
    }
}