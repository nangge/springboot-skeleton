package com.nango.skeletonweb.infrastructure.persistence.order.dao.impl;

import com.nango.skeletonweb.infrastructure.persistence.order.entity.Order;
import com.nango.skeletonweb.infrastructure.persistence.order.mapper.OrderMapper;
import com.nango.skeletonweb.infrastructure.persistence.order.dao.OrderDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author nango
 * @since 2022-04-03
 */
@Service
public class OrderDaoImpl extends ServiceImpl<OrderMapper, Order> implements OrderDao {

}
