package com.nango.skeletonweb.infrastructure.persistence.seckill.dao.impl;

import com.nango.skeletonweb.infrastructure.persistence.seckill.entity.Seckill;
import com.nango.skeletonweb.infrastructure.persistence.seckill.mapper.SeckillMapper;
import com.nango.skeletonweb.infrastructure.persistence.seckill.dao.SeckillDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀表 服务实现类
 * </p>
 *
 * @author nango
 * @since 2022-04-03
 */
@Service
public class SeckillDaoImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillDao {

}
