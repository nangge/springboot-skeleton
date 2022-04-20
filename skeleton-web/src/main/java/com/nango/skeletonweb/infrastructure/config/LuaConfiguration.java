package com.nango.skeletonweb.infrastructure.config;

import com.nango.skeletonweb.infrastructure.exception.BizException;
import com.nango.skeletonweb.infrastructure.exception.ErrorCodeEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author wyn
 * @package com.nango.skeletonweb.infrastructure.config
 * @class LuaConfiguration
 * @date 2022/4/5 20:15
 * @description Lua
 *
 */
@Configuration
public class LuaConfiguration {
    @Bean
    public DefaultRedisScript<Long> seckillScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/seckill.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 秒杀结果处理
     * @param r
     * @return
     */
    public static boolean dealSeckillResult(Long r) {
        if (r.equals(-1)) {
            throw new BizException(ErrorCodeEnum.SECKILL_NOT_EXISTS);
        }

        if (r.equals(-2)) {
            throw new BizException(ErrorCodeEnum.SECKILL_HAD_JOIN);
        }

        if (r.equals(-3)) {
            throw new BizException(ErrorCodeEnum.SECKILL_STOCK_NOT_ENOUGH);
        }

        return true;
    }
}
