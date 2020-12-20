package com.none.sharding.infrastruc.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.none.sharding.domain.entity.ProductDO;
import com.none.sharding.domain.service.ProductService;
import com.none.sharding.infrastruc.persistence.mapper.ProductNewMapper;
import org.springframework.stereotype.Service;

/**
 * @Author nango
 * @Date 2020/12/20
 * @Description service
 */
@Service
public class ProductNewServiceImpl
        extends ServiceImpl<ProductNewMapper, ProductDO>
        implements ProductService {
}
