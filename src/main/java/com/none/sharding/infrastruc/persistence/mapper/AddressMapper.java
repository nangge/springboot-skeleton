package com.none.sharding.infrastruc.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.none.sharding.domain.entity.AddressDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Mapper
public interface AddressMapper extends BaseMapper<AddressDO> {
    /**
     * 保存
     */
    void save(AddressDO addressDO);

    /**
     * 查询
     * @param id
     * @return
     */
    AddressDO get(Long id);
}
