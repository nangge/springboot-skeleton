package com.nango.skeletonweb.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nango.skeletonweb.domain.entity.ProductDO;
import com.nango.skeletonweb.domain.entity.ProductNewDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author wyn
 * @Date 2020/12/19
 * @Description
 */
@Mapper
public interface ProductNewMapper
        extends BaseMapper<ProductNewDO> {
    /**
     * 执行sql
     * @param sql
     * @return
     */
    @Update({"${sql}"})
    int execSQL(@Param("sql") String sql);
}
