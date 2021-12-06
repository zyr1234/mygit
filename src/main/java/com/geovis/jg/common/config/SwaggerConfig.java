package com.geovis.jg.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
    return new Docket(DocumentationType.SWAGGER_2)
    .pathMapping("/")
    .select()
    .apis(RequestHandlerSelectors.basePackage("com.geovis.jg"))
     //       .apis(RequestHandlerSelectors.basePackage("com.geovis.jg.qt.controller"))
    .paths(PathSelectors.any())
    .build().apiInfo(new ApiInfoBuilder()
    .title("MB数据管理系统")
    .description("基于Spring boot的MB数据管理系统的接口文档，简单易用的Restful风格")
    .version("v1.0")
    .contact(new Contact("张三","www.geovis.com","zhangsan@geovis.com"))
    .license("The Appache License")
    .licenseUrl("wwww.GEOVIS.com")
    .build());//.enable(enable);
    }
}
