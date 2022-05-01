package com.nango.skeletonweb.infrastructure.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nango.skeletonweb.domain.entity.ProductDO;
import com.nango.skeletonweb.domain.service.ProductService;
import com.nango.skeletonweb.infrastructure.persistence.mapper.ProductNewMapper;
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
