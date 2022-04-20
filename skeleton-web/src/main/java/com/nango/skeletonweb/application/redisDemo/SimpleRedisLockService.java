package com.nango.skeletonweb.application.redisDemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wyn
 * @package com.nango.skeletonweb.application.redisDemo
 * @class SimpleRedisLock
 * @date 2022/3/9 15:27
 * @description 分布式锁简单示例
 * set
 * expire
 * del
 */
@Slf4j
@Service
public class SimpleRedisLockService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 尝试加锁
     * @param k
     * @param v
     * @param expire
     * @return
     */
    public Boolean getLock(String k, String v, Long expire) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            return valueOperations.setIfPresent(k, v, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("加锁失败 {}", e);
            return false;
        }
    }

    /**
     * 释放锁
     * @param k
     * @param v
     * @return
     */
    public Boolean releaseLock(String k, String v) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            if (StringUtils.equals(valueOperations.get(k), v)) {
                return redisTemplate.delete(k);
            }
            return true;
        } catch (Exception e) {
            log.error("加锁失败 {}", e);
            return false;
        }
    }
}
