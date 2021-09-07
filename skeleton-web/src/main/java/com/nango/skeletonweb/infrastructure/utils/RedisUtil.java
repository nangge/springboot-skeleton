package com.nango.skeletonweb.infrastructure.utils;

import com.nango.skeletonweb.infrastructure.exception.BizException;
import com.nango.skeletonweb.infrastructure.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.client.codec.Codec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisLock 分布式锁
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 默认缓存时间
     */
    private static final Long DEFAULT_EXPIRED = 5 * 60L;

    /**
     * 暴露redisson的Lock对象
     *
     * @param key
     * @return
     */
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    public boolean tryLockTimeout(String lockKey, long waitTime, long leaseTime) {
        try {
            return this.getLock(lockKey)
                    .tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("redis lock error e:{}", e);
            throw new BizException(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public void unLock(String lockKey) {
        try {
            this.getLock(lockKey).unlock();
        } catch (Exception e) {
            log.info("redis unlock error e:{}", e);
            throw new BizException(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 读取缓存
     *
     * @param key 缓存key
     * @param <V>
     * @return 缓存返回值
     */
    public <V> V get(String key) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }


    /**
     * 读取缓存
     *
     * @param key   缓存key
     * @param <V>
     * @param codec value编码解码方式
     * @return 缓存返回值
     * @like https://yq.aliyun.com/articles/551642/
     */
    public <V> V get(String key, Codec codec) {
        RBucket<V> bucket = redissonClient.getBucket(key, codec);
        return bucket.get();
    }


    /**
     * rmap中获取
     */
    public <K, V> V get(String key, String hashKey, Codec codec) {
        RMap<K, V> map = redissonClient.getMap(key, codec);
        return map.get(hashKey);
    }


    /**
     * rmap中获取
     */
    public <K, V> V get(String key, String hashKey) {
        RMap<K, V> map = redissonClient.getMap(key);
        return map.get(hashKey);
    }


    /**
     * rmap中设置值
     */
    public <K, V> V put(String key, K hashKey, V v) {
        RMap<K, V> map = redissonClient.getMap(key);
        return map.put(hashKey, v);
    }

    /**
     * rmap中设置值
     */
    public <K, V> V put(String key, K hashKey, V v, Codec codec) {
        RMap<K, V> map = redissonClient.getMap(key, codec);
        return map.put(hashKey, v);
    }

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param <V>
     */
    public <V> void set(String key, V value) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 获取set格式的缓存
     *
     * @param key 缓存key
     * @return
     */
    public <V> RSet<V> getRSet(String key) {
        return redissonClient.getSet(key);
    }

    /**
     * 获取set格式的缓存
     *
     * @param key 缓存key
     * @return
     */
    public <V> Set<V> getSets(String key) {
        return getRSet(key);
    }

    /**
     * 设置缓存,不设置过期时间
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param <V>
     */
    public <V> void getAndSet(String key, V value) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 设置缓存
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expired 缓存过期时间
     * @param <V>     类型
     */
    public <V> void set(String key, V value, long expired) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        bucket.set(value, expired <= 0 ? DEFAULT_EXPIRED : expired, TimeUnit.SECONDS);
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public void remove(String key) {
        redissonClient.getBucket(key).delete();
    }


    /**
     * 移除缓存,map
     *
     * @param key
     */
    public void remove(String key, String hashKey) {
        redissonClient.getMap(key).remove(hashKey);
    }


    /**
     * 设置过期时间
     *
     * @param key
     */
    public void expire(String key) {
        redissonClient.getBucket(key).expire(10, TimeUnit.SECONDS);
    }

    /**
     * 自定义设置设置过期时间
     *
     * @param key
     * @param time
     * @param timeType
     */
    public void expire(String key, long time, TimeUnit timeType) {
        redissonClient.getBucket(key).expire(time, timeType);
    }

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    /**
     * 判断缓存是否存在 针对hash数据结构
     *
     * @param key
     * @return
     */
    public boolean exists(String key, String hashKey) {
        return redissonClient.getMap(key).containsKey(hashKey);
    }

    /**
     * 暴露redisson的RList对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> RList<T> getRList(String key) {
        return redissonClient.getList(key);
    }

    /**
     * 暴露redisson的List结果
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getLists(String key) {
        return getRList(key);
    }


    /**
     * 暴露redisson的RMap对象
     *
     * @param key
     * @param <K, V>
     * @return
     */
    public <K, V> RMap<K, V> getRMap(String key) {
        return redissonClient.getMap(key);
    }

    /**
     * 原子的获取某个key递增后的值
     *
     * @param key key
     * @return 更新后的值
     */
    public long incrementAndGet(String key) {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(key);
        return rAtomicLong.incrementAndGet();
    }

    /**
     * 初始化原子long 0
     *
     * @param key
     */
    public void atomicSetZero(String key) {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(key);
        rAtomicLong.set(0);
    }

}
