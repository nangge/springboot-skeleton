package com.none.sharding.infrastruc.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.none.sharding.domain.entity.UserDO;
import com.none.sharding.domain.entity.UserOrderDO;
import com.none.sharding.domain.service.UserOrderService;
import com.none.sharding.domain.service.UserService;
import com.none.sharding.infrastruc.persistence.mapper.UserMapper;
import com.none.sharding.infrastruc.persistence.mapper.UserOrderMapper;
import org.springframework.stereotype.Service;

/**
 * @Author wyn
 * @Date 2020/11/10
 * @Description service
 */
@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, UserDO>
        implements UserService {
}
