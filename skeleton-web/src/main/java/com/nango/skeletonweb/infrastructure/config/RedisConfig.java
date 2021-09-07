package com.nango.skeletonweb.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedisConfig
 * @Description redis配置类
 * @Author nango
 * @Date 2021/09/06 10:17
 **/
@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress("redis://" + host + ":" + port).setPingConnectionInterval(60);
        singleServerConfig.setDatabase(database == null? 0:database);
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        log.info("------------- redisson -----------------------");
        log.info("TransportMode:{}", config.getTransportMode());
        return Redisson.create(config);
    }
}
