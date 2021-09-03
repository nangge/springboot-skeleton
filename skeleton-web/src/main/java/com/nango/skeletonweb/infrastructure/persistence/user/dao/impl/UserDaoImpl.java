package com.nango.skeletonweb.infrastructure.persistence.user.dao.impl;

import com.nango.skeletonweb.infrastructure.persistence.user.entity.User;
import com.nango.skeletonweb.infrastructure.persistence.user.mapper.UserMapper;
import com.nango.skeletonweb.infrastructure.persistence.user.dao.UserDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nango
 * @since 2021-09-03
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {

}
