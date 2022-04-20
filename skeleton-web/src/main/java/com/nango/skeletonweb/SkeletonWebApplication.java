package com.nango.skeletonweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages={"com.nango.skeletonweb.infrastructure.persistence.**.mapper"})
public class SkeletonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkeletonWebApplication.class, args);
    }

}
