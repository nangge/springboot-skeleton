package com.nango.skeletonweb.infrastructure.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nango.skeletonweb.domain.entity.UserDO;
import com.nango.skeletonweb.domain.service.UserService;
import com.nango.skeletonweb.infrastructure.persistence.mapper.UserMapper;
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
