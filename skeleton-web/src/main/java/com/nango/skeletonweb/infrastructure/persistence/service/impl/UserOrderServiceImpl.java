package com.nango.skeletonweb.infrastructure.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nango.skeletonweb.domain.entity.UserOrderDO;
import com.nango.skeletonweb.domain.service.UserOrderService;
import com.nango.skeletonweb.infrastructure.persistence.mapper.UserOrderMapper;
import org.springframework.stereotype.Service;

/**
 * @Author wyn
 * @Date 2020/11/10
 * @Description 订单service
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrderDO>
        implements UserOrderService {
}
