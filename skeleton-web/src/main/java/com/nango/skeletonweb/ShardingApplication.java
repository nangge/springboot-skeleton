package com.nango.skeletonweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.nango.skeletonweb.infrastructure.persistence.mapper", "com.nango.skeletonweb.infrastructure.persistenceOld.mapper"})
public class ShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }

}
