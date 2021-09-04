package com.nango.skeletonweb.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * @Author nango
 * @Date 2021-09-04 21：24
 * @Description 公共线程池
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

  @Bean("taskExecutor")
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2);
    threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 3);
    threadPoolTaskExecutor.setQueueCapacity(100);
    threadPoolTaskExecutor.setKeepAliveSeconds(60);
    threadPoolTaskExecutor.setThreadNamePrefix("taskExecutor-");
    threadPoolTaskExecutor.setRejectedExecutionHandler(new CallerRunsPolicy());
    return threadPoolTaskExecutor;
  }
}
