package com.nango.skeletonweb.infrastructure.persistenceOld.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nango.skeletonweb.domain.entity.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author wyn
 * @Date 2020/12/19
 * @Description
 */
@Mapper
public interface ProductMapper
        extends BaseMapper<ProductDO> {
    /**
     * 执行更新
     * @param sql
     * @return
     */
    @Update({"${sql}"})
    int execUpdateSQL(@Param("sql") String sql);

    /**
     * 执行插入
     * @param sql
     * @return
     */
    @Update({"${sql}"})
    int execInsertSQL(@Param("sql") String sql);
}
