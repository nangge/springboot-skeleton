package com.nango.skeletonweb.application.seckill.impl;

import com.nango.skeletonweb.application.seckill.SeckillService;
import com.nango.skeletonweb.infrastructure.CommonConstants;
import com.nango.skeletonweb.infrastructure.config.LuaConfiguration;
import com.nango.skeletonweb.infrastructure.persistence.seckill.dao.SeckillDao;
import com.nango.skeletonweb.infrastructure.persistence.seckill.entity.Seckill;
import com.nango.skeletonweb.interfaces.dto.DoSeckillDTO;
import com.nango.skeletonweb.interfaces.dto.SeckillAddDTO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author wyn
 * @package com.nango.skeletonweb.application.seckill.impl
 * @class SeckillServiceImpl
 * @date 2022/4/4 21:10
 * @description 秒杀
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private SeckillDao seckillDao;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Resource
    private DefaultRedisScript<Long> seckillScript;

    public void addSeckill(SeckillAddDTO seckillAddDTO) {
        try {
            Seckill seckill = new Seckill();
            BeanUtils.copyProperties(seckillAddDTO, seckill);
            seckillDao.save(seckill);

            //预热
            redisTemplate.opsForValue().set(CommonConstants.RedisKeys.SECKILL_KEY + seckill.getId(),
                    seckillAddDTO.getStock());
        } catch (Exception e) {
            log.error("创建秒杀活动失败 {}", e);
        }
    }

    public void doSeckill(DoSeckillDTO doSeckillDTO) {
        try {

            Long exeRes = redisTemplate.execute(seckillScript,
                    Arrays.asList(CommonConstants.RedisKeys.SECKILL_KEY + doSeckillDTO.getSeckillId(),
                            doSeckillDTO.getUserId().toString()),
                    doSeckillDTO.getNum().toString());
            //异步处理订单
            if (LuaConfiguration.dealSeckillResult(exeRes)) {
                //推送mq处理
            }

        } catch (Exception e) {
            log.error("抢购失败 {}", e);
        }
    }
}
