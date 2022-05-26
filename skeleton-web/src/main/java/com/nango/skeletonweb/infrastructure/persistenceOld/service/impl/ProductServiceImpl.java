package com.nango.skeletonweb.infrastructure.persistenceOld.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nango.skeletonweb.domain.entity.ProductDO;
import com.nango.skeletonweb.domain.service.ProductService;
import com.nango.skeletonweb.infrastructure.persistence.mapper.ProductNewMapper;
import com.nango.skeletonweb.infrastructure.persistenceOld.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
 * @Author nango
 * @Date 2020/12/20
 * @Description service
 */
@Service
public class ProductServiceImpl
        extends ServiceImpl<ProductMapper, ProductDO>
        implements ProductService {
}
