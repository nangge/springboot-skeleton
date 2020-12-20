package com.none.sharding.infrastruc.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.none.sharding.domain.entity.UserOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wyn
 * @Date 2020/12/19
 * @Description
 */
@Mapper
public interface UserOrderMapper
        extends BaseMapper<UserOrderDO> {
}
