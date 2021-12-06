package com.geovis.jg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import tk.mybatis.spring.annotation.MapperScan;

@EnableRabbit
@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = {"com.geovis.jg.mb.mapper"})
public class mbRefactor {
    public static void main(String[] args){
        SpringApplication.run(mbRefactor.class,args);
    }
}
