package com.nango.skeletonweb.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nango.skeletonweb.domain.entity.ProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wyn
 * @Date 2020/12/19
 * @Description
 */
@Mapper
public interface ProductNewMapper
        extends BaseMapper<ProductDO> {
}
